package test.aperture.web.rest;

import aperture.config.SpringBootApertureTestingConfiguration;
import aperture.model.TestSupervisor;
import aperture.repository.TestSupervisorRepository;
import aperture.service.TestSupervisorService;
import aperture.web.rest.TestSupervisorResource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Tests on TestSupervisor rest resource.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootApertureTestingConfiguration.class)
public class TestSupervisorResourceTests {
    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private TestSupervisorService testSupervisorService;

    @Autowired
    private TestSupervisorRepository testSupervisorRepository;

    private MockMvc mockMvc;

    // -- Variables used for tests
    private TestSupervisor supervisorGlados = new TestSupervisor(
        "5063114bd386d8fadbd6b004",
        "glados@aperture.fr",
        "caroline"
    );
    private TestSupervisor supervisorGladosUpdated = new TestSupervisor(
        "5063114bd386d8fadbd6b004",
        "glados_updated@aperture.fr",
        "caroline"
    );
    private TestSupervisor supervisorWrongUpdated = new TestSupervisor(
        "5063114bd386d8fadbd6b",
        "WRONG UPDATED LOGIN",
        "WRONG UPDATED PASS"
    );
    private TestSupervisor newSupervisor = new TestSupervisor(
        "0",
        "supervisortestlogin",
        "supervisortestpass"
    );

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TestSupervisorResource testSupervisorResource = new TestSupervisorResource(testSupervisorService,
            testSupervisorRepository);
        this.mockMvc = MockMvcBuilders.standaloneSetup(testSupervisorResource)
            .setMessageConverters(jacksonMessageConverter)
            .build();

        TestUtil.executeAllMongeezScripts();
    }

    // -- HttpStatus codes tests

    @Test
    public void shouldReturn200SupervisorsRoute() throws Exception {
        mockMvc.perform(get("/api/supervisors"))
            .andExpect(status().isOk());
    }

    @Test
    public void shouldReturn200SupervisorIdFoundRoute() throws Exception {
        mockMvc.perform(get("/api/supervisors/id/" + supervisorGlados.getId()))
            .andExpect(status().isOk());
    }

    @Test
    public void shouldReturn204SupervisorIdNotFoundRoute() throws Exception {
        mockMvc.perform(get("/api/supervisors/id/42"))
            .andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturn204SupervisorIncorrectLoginRoute() throws Exception {
        mockMvc.perform(post("/api/supervisors/login").param("login", "wronglogin"))
            .andExpect(status().isNoContent());
    }

    // -- Content returned tests

    @Test
    public void shouldContainElementsAndGladosSupervisorAllSupervisorsRoute() throws Exception {
        String jsonPathExpression = "$.[?(@.id==\"" + supervisorGlados.getId() + "\")]";

        mockMvc.perform(get("/api/supervisors"))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isNotEmpty())
            .andExpect(jsonPath(jsonPathExpression).isNotEmpty())
            .andExpect(jsonPath(jsonPathExpression + ".login").value(supervisorGlados.getLogin()))
            .andExpect(jsonPath(jsonPathExpression + ".pass").value(supervisorGlados.getPass()));
    }

    @Test
    public void shouldReturnElementSupervisorLoginRoute() throws Exception {
        String jsonPathExpression = "$.[?(@.login==\"" + supervisorGlados.getLogin() + "\")]";

        mockMvc.perform(post("/api/supervisors/login").param("login", supervisorGlados.getLogin()))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isNotEmpty())
            .andExpect(jsonPath(jsonPathExpression).isNotEmpty())
            .andExpect(jsonPath(jsonPathExpression + ".id").value(supervisorGlados.getId()))
            .andExpect(jsonPath(jsonPathExpression + ".pass").value(supervisorGlados.getPass()));
    }

    // -- Mutability handling operations tests (Create/Delete/Update)

    @Test
    public void should200AndReturnNewTestSupervisorWithIdCreateRoute() throws Exception {
        String jsonPathExpression = "$.[?(@.login==\"" + newSupervisor.getLogin() + "\")]";
        int databaseSizeBeforeCreate = testSupervisorRepository.findAll().size();

        mockMvc.perform(post("/api/supervisors/create")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(newSupervisor))
        )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath(jsonPathExpression).isNotEmpty());

        int databaseSizeAfterCreate = testSupervisorRepository.findAll().size();
        assertThat(databaseSizeAfterCreate > databaseSizeBeforeCreate);
    }

    @Test
    public void should400NewTestSupervisorWithExistantLoginCreateRoute() throws Exception {
        int databaseSizeBeforeCreate = testSupervisorRepository.findAll().size();

        mockMvc.perform(post("/api/supervisors/create")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supervisorGlados))
        )
            .andExpect(status().isBadRequest());

        int databaseSizeAfterCreate = testSupervisorRepository.findAll().size();
        assertThat(databaseSizeAfterCreate == databaseSizeBeforeCreate);
    }

    @Test
    public void should200AndReturnUpdatedTestSupervisor() throws Exception {
        String jsonPathExpression = "$.[?(@.login==\"" + supervisorGladosUpdated.getLogin() + "\")]";

        mockMvc.perform(put("/api/supervisors/update")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supervisorGladosUpdated))
        )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath(jsonPathExpression).isNotEmpty());

        testSupervisorRepository.save(supervisorGlados); // Put back to default
    }

    @Test
    public void should400UpdateInexistantTestSupervisor() throws Exception {
        mockMvc.perform(put("/api/supervisors/update")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supervisorWrongUpdated))
        )
            .andExpect(status().isBadRequest());
    }

    @Test
    public void should200AndReturnDeletedRoomWithIdDeleteRoute() throws Exception {
        String jsonPathExpression = "$.[?(@.login==\"" + supervisorGlados.getLogin() + "\")]";

        TestSupervisor supervisorToDelete = testSupervisorRepository.save(supervisorGlados);
        int databaseSizeBeforeDelete = testSupervisorRepository.findAll().size();

        mockMvc.perform(delete("/api/supervisors/delete/" + supervisorToDelete.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath(jsonPathExpression).isNotEmpty());


        int databaseSizeAfterDelete = testSupervisorRepository.findAll().size();
        assertThat(databaseSizeAfterDelete < databaseSizeBeforeDelete);
    }

    @Test
    public void should400DeleteInexistantRoomDeleteRoute() throws Exception {
        int databaseSizeBeforeDelete = testSupervisorRepository.findAll().size();

        mockMvc.perform(delete("/api/supervisors/delete/perlimpinpin"))
            .andExpect(status().isBadRequest());

        int databaseSizeAfterDelete = testSupervisorRepository.findAll().size();
        assertThat(databaseSizeAfterDelete == databaseSizeBeforeDelete);
    }

}
