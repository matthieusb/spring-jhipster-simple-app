package main.aperture.web.rest;

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
    private TestSupervisor SUPERVISOR_GLADOS = new TestSupervisor("5063114bd386d8fadbd6b004", "glados@aperture.fr", "caroline");
    private TestSupervisor SUPERVISOR_GLADOS_UPDATED = new TestSupervisor("5063114bd386d8fadbd6b004", "glados_updated@aperture.fr", "caroline");
    private TestSupervisor SUPERVISOR_WRONG_UPDATED = new TestSupervisor("5063114bd386d8fadbd6b", "WRONG UPDATED LOGIN", "WRONG UPDATED PASS");
    private TestSupervisor NEW_SUPERVISOR = new TestSupervisor("0", "supervisortestlogin", "supervisortestpass");

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TestSupervisorResource testSupervisorResource = new TestSupervisorResource(testSupervisorService, testSupervisorRepository);
        this.mockMvc = MockMvcBuilders.standaloneSetup(testSupervisorResource)
            .setMessageConverters(jacksonMessageConverter)
            .build();

        TestUtil.executeAllMongeezScripts();
    }

//    @After
//    public void putBackInPlace() {
//        TestUtil.executeAllMongeezScripts();
//    }

    // -- HttpStatus codes tests

    @Test
    public void shouldReturn200SupervisorsRoute() throws Exception {
        mockMvc.perform(get("/api/supervisors"))
            .andExpect(status().isOk());
    }

    @Test
    public void shouldReturn200SupervisorIdFoundRoute() throws Exception {
        mockMvc.perform(get("/api/supervisors/id/" + SUPERVISOR_GLADOS.getId()))
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
        String jsonPathExpression = "$.[?(@.id==\"" + SUPERVISOR_GLADOS.getId() + "\")]";

        mockMvc.perform(get("/api/supervisors"))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isNotEmpty())
            .andExpect(jsonPath(jsonPathExpression).isNotEmpty())
            .andExpect(jsonPath(jsonPathExpression + ".login").value(SUPERVISOR_GLADOS.getLogin()))
            .andExpect(jsonPath(jsonPathExpression + ".pass").value(SUPERVISOR_GLADOS.getPass()));
    }

    @Test
    public void shouldReturnElementSupervisorLoginRoute() throws Exception {
        String jsonPathExpression = "$.[?(@.login==\"" + SUPERVISOR_GLADOS.getLogin() + "\")]";

        mockMvc.perform(post("/api/supervisors/login").param("login", SUPERVISOR_GLADOS.getLogin()))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isNotEmpty())
            .andExpect(jsonPath(jsonPathExpression).isNotEmpty())
            .andExpect(jsonPath(jsonPathExpression + ".id").value(SUPERVISOR_GLADOS.getId()))
            .andExpect(jsonPath(jsonPathExpression + ".pass").value(SUPERVISOR_GLADOS.getPass()));
    }

    // -- Mutability handling operations tests (Create/Delete/Update)

    @Test
    public void should200AndReturnNewTestSupervisorWithIdCreateRoute() throws Exception {
        String jsonPathExpression = "$.[?(@.login==\"" + NEW_SUPERVISOR.getLogin() + "\")]";
        int databaseSizeBeforeCreate = testSupervisorRepository.findAll().size();

        mockMvc.perform(post("/api/supervisors/create")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(NEW_SUPERVISOR))
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
            .content(TestUtil.convertObjectToJsonBytes(SUPERVISOR_GLADOS))
        )
            .andExpect(status().isBadRequest());

        int databaseSizeAfterCreate = testSupervisorRepository.findAll().size();
        assertThat(databaseSizeAfterCreate == databaseSizeBeforeCreate);
    }

    @Test
    public void should200AndReturnUpdatedTestSupervisor() throws Exception {
        String jsonPathExpression = "$.[?(@.login==\"" + SUPERVISOR_GLADOS_UPDATED.getLogin() + "\")]";

        mockMvc.perform(put("/api/supervisors/update")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(SUPERVISOR_GLADOS_UPDATED))
        )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath(jsonPathExpression).isNotEmpty());

        testSupervisorRepository.save(SUPERVISOR_GLADOS); // Put back to default
    }

    @Test
    public void should400UpdateInexistantTestSupervisor() throws Exception {
        mockMvc.perform(put("/api/supervisors/update")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(SUPERVISOR_WRONG_UPDATED))
        )
            .andExpect(status().isBadRequest());
    }

    @Test
    public void should200AndReturnDeletedRoomWithIdDeleteRoute() throws Exception {
        String jsonPathExpression = "$.[?(@.login==\"" + SUPERVISOR_GLADOS.getLogin() + "\")]";

        TestSupervisor supervisorToDelete = testSupervisorRepository.save(SUPERVISOR_GLADOS);
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
