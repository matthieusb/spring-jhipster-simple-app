package test.aperture.controller;

import aperture.config.SpringBootApertureApiConfiguration;
import aperture.controller.HomeController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests on home controller.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootApertureApiConfiguration.class)
public class HomeControllerTest {

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        HomeController homeController = new HomeController();

        this.mockMvc = MockMvcBuilders.standaloneSetup(homeController)
            .setMessageConverters(jacksonMessageConverter)
            .build();
    }

    @Test
    public void shouldReturn302RoomsRoute() throws Exception {
        mockMvc.perform(get("/"))
            .andExpect(status().is3xxRedirection());
    }
}
