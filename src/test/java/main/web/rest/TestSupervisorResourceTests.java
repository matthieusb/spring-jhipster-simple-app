package main.web.rest;

import config.SpringBootApertureTestingConfiguration;
import model.TestSupervisor;
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
import repository.TestSupervisorRepository;
import web.rest.TestSupervisorResource;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootApertureTestingConfiguration.class)
public class TestSupervisorResourceTests {
    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private TestSupervisorRepository testSupervisorRepository;

    private MockMvc mockMvc;

    // -- Variables used for tests
    private TestSupervisor SUPERVISOR_GLADOS = new TestSupervisor("5063114bd386d8fadbd6b004", "glados@aperture.fr", "caroline");

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TestSupervisorResource testSupervisorResource = new TestSupervisorResource(testSupervisorRepository);
        this.mockMvc = MockMvcBuilders.standaloneSetup(testSupervisorResource)
            .setMessageConverters(jacksonMessageConverter)
            .build();
    }

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
}
