package integration.controllerstest;

import config.SpringBootApertureTestingConfiguration;
import model.TestSupervisor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.assertj.core.api.BDDAssertions.then;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootApertureTestingConfiguration.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"management.port=0"})

public class TestSupervisorResourceTests {

    // -- System variables
    @LocalServerPort
    private int port;

    @Value("${local.management.port}")
    private int mgt;

    @SuppressWarnings("SpringJavaAutowiredMembersInspection")
    @Autowired
    private TestRestTemplate testRestTemplate;

    public void setPort(int port) {
        this.port = port;
    }

    // -- Variables used for tests
    private String hostPathWithPort;
    private TestSupervisor supervisorGlados;
    private HttpHeaders headersFormUrlEncoded;

    @Before
    public void setup() {
        this.hostPathWithPort = "http://localhost:" + this.port + "/api";
        supervisorGlados = new TestSupervisor("5063114bd386d8fadbd6b004", "glados@aperture.fr", "caroline");

        headersFormUrlEncoded = new HttpHeaders();
        headersFormUrlEncoded.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    }

    // -- HttpStatus codes tests

    @Test
    public void shouldReturn200SupervisorsRoute() throws Exception {
        @SuppressWarnings("rawtypes")
        ResponseEntity<TestSupervisor[]> entity = this.testRestTemplate.getForEntity(
                hostPathWithPort + "/supervisors", TestSupervisor[].class
        );

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void shouldReturn200SupervisorIdFoundRoute() throws Exception {
        @SuppressWarnings("rawtypes")
        ResponseEntity<TestSupervisor> entity = this.testRestTemplate.getForEntity(
                hostPathWithPort + "/supervisors/id/" + supervisorGlados.getId(), TestSupervisor.class
        );

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void shouldReturn204SupervisorIdNotFoundRoute() throws Exception {
        @SuppressWarnings("rawtypes")
        ResponseEntity<TestSupervisor> entity = this.testRestTemplate.getForEntity(
                hostPathWithPort + "/supervisors/id/42", TestSupervisor.class
        );

        then(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    public void shouldReturn204SupervisorIncorrectLoginRoute() throws Exception {
        @SuppressWarnings("rawtypes")
        MultiValueMap<String, String> parametersToSend = new LinkedMultiValueMap<>();
        parametersToSend.add("login", "wronglogin");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(parametersToSend, this.headersFormUrlEncoded);

        ResponseEntity<TestSupervisor> entity = this.testRestTemplate.postForEntity(
                hostPathWithPort + "/supervisors/login", request, TestSupervisor.class
        );

        then(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    // -- Content returned tests

    @Test
    public void shouldContainElementsAndGladosSupervisorAllSupervisorsRoute() throws Exception {
        @SuppressWarnings("rawtypes")
        ResponseEntity<TestSupervisor[]> entity = this.testRestTemplate.getForEntity(
                hostPathWithPort + "/supervisors", TestSupervisor[].class
        );

        then(entity.getBody()).isNotEmpty();
        then(entity.getBody()).contains(supervisorGlados);
    }

    @Test
    public void shouldReturnElementSupervisorLoginRoute() throws Exception {
        @SuppressWarnings("rawtypes")

        MultiValueMap<String, String> parametersToSend = new LinkedMultiValueMap<>();
        parametersToSend.add("login", supervisorGlados.getLogin());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(parametersToSend, this.headersFormUrlEncoded);
        ResponseEntity<TestSupervisor> entity = this.testRestTemplate.postForEntity(
                hostPathWithPort + "/supervisors/login", request, TestSupervisor.class
        );

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).isEqualToComparingFieldByField(supervisorGlados);
    }


}
