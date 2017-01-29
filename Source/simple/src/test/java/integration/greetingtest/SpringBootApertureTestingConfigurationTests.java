package integration.greetingtest;

import config.SpringBootApertureTestingConfiguration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Map;

import static org.assertj.core.api.BDDAssertions.then;

/**
 * This class tests if every url of this app returns the expected HTTP STATUS
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootApertureTestingConfiguration.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"management.port=0"})
public class SpringBootApertureTestingConfigurationTests {

    @LocalServerPort
    private int port;

    @Value("${local.management.port}")
    private int mgt;

    @SuppressWarnings("SpringJavaAutowiredMembersInspection")
    @Autowired
    private TestRestTemplate testRestTemplate;

    /* PERSONAL ROUTES */
    @Test
    public void shouldReturn200GreetingRoute() throws Exception {
        @SuppressWarnings("rawtypes")
        ResponseEntity<Map> entity = this.testRestTemplate.getForEntity(
                "http://localhost:" + this.port + "/greeting", Map.class
        );

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void shouldReturn200RoomsRoute() throws Exception {
        @SuppressWarnings("rawtypes")
        ResponseEntity<Object[]> entity = this.testRestTemplate.getForEntity(
                "http://localhost:" + this.port + "/rooms", Object[].class
        );

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    /* SYSTEM ROUTES */
    @Test
    public void shouldReturn200Health() throws Exception {
        @SuppressWarnings("rawtypes")
        ResponseEntity<Map> entity = this.testRestTemplate.getForEntity(
                "http://localhost:" + this.mgt + "/health", Map.class
        );

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void shouldReturn200InfoRoute() throws Exception {
        @SuppressWarnings("rawtypes")
        ResponseEntity<Map> entity = this.testRestTemplate.getForEntity(
                "http://localhost:" + this.mgt + "/info", Map.class
        );

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

}
