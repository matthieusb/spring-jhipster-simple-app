package main.web.rest;

import config.SpringBootApertureTestingConfiguration;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import web.rest.GreetingsResource;

import java.util.Map;

import static org.assertj.core.api.BDDAssertions.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootApertureTestingConfiguration.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"management.port=0"})

public class GreetingsResourceTests {

    @LocalServerPort
    private int port;

    @Value("${local.management.port}")
    private int mgt;

    @SuppressWarnings("SpringJavaAutowiredMembersInspection")
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        GreetingsResource greetingsResource = new GreetingsResource();

        this.mockMvc = MockMvcBuilders.standaloneSetup(greetingsResource)
            .setMessageConverters(jacksonMessageConverter)
            .build();
    }

    @Test
    public void shouldReturn200GreetingRoute() throws Exception {
        mockMvc.perform(get("/api/greeting"))
            .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnAValidGreetingJsonWithSimpleHelloWorld() throws Exception {
//        mockMvc.perform(get("/api/greeting"))
//            .andExpect(status().isOk())
//            .andExpect();


        @SuppressWarnings("rawtypes")
        ResponseEntity<Map> entity = this.testRestTemplate.getForEntity(
            "http://localhost:" + this.port + "/api/greeting", Map.class
        );

        Map responseBody = entity.getBody();

        then(responseBody.containsKey("id") && responseBody.containsKey("content"));
    }
}
