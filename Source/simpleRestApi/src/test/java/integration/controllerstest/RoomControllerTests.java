package integration.controllerstest;


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

import static org.assertj.core.api.BDDAssertions.then;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootApertureTestingConfiguration.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"management.port=0"})

public class RoomControllerTests {

    @LocalServerPort
    private int port;

    @Value("${local.management.port}")
    private int mgt;

    @SuppressWarnings("SpringJavaAutowiredMembersInspection")
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void shouldReturn200RoomsRoute() throws Exception {
        @SuppressWarnings("rawtypes")
        ResponseEntity<Object[]> entity = this.testRestTemplate.getForEntity(
                "http://localhost:" + this.port + "/rooms", Object[].class
        );

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    //TODO FINISH THESES TESTS
//    @Test
//    public void shouldReturn200RoomsIdRoute() throws Exception {
//        ResponseEntity<Object> entity = this.testRestTemplate.getForEntity(
//            "http://localhost:" + this.port + "/rooms/id/5465465465465456f"
//        )
//    }

    public void setPort(int port) {
        this.port = port;
    }
}
