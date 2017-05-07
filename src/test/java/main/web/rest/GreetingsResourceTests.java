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
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import web.rest.GreetingsResource;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootApertureTestingConfiguration.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"management.port=0"})

public class GreetingsResourceTests {

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
        mockMvc.perform(get("/api/greeting"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("id").exists())
            .andExpect(jsonPath("content").exists())
            .andExpect(jsonPath("id").isNumber())
            .andExpect(jsonPath("content").isString())
            .andExpect(jsonPath("content").value("Hello, Stranger"));
    }
}
