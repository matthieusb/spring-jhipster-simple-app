package integration.controllerstest;


import config.SpringBootApertureTestingConfiguration;
import model.Room;
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

public class RoomControllerTests {

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
    private Room room42;

    @Before
    public void setup() {
        hostPathWithPort = "http://localhost:" + this.port;
        room42 = new Room("5063114bd386d8fadbd6b00a", 42, "Answer to life room");
    }


    // -- HttpStatus codes tests

    @Test
    public void shouldReturn200RoomsRoute() throws Exception {
        @SuppressWarnings("rawtypes")
        ResponseEntity<Room[]> entity = this.testRestTemplate.getForEntity(
                hostPathWithPort + "/rooms", Room[].class
        );

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void shouldReturn200RoomsIdFoundRoute() throws Exception {
        @SuppressWarnings("rawtypes")
        ResponseEntity<Room> entity = testRestTemplate.getForEntity(
            hostPathWithPort + "/rooms/id/5063114bd386d8fadbd6b00a", Room.class
        );

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void shouldReturn204RoomsIdNotFoundRoute() throws Exception {
        @SuppressWarnings("rawtypes")
        ResponseEntity<Room> entity = this.testRestTemplate.getForEntity(
            hostPathWithPort + "/rooms/id/42", Room.class
        );

        then(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    // -- Content returned tests

    @Test
    public void shouldReturnElementsRoomsRoute() throws Exception {
        @SuppressWarnings("rawtypes")
        ResponseEntity<Room[]> entity = this.testRestTemplate.getForEntity(
                hostPathWithPort + "/rooms", Room[].class
        );

        then(entity.getBody()).isNotEmpty();
    }

    @Test
    public void shouldReturnElementRoomsIdRoute() throws Exception {
        @SuppressWarnings("rawtypes")
        ResponseEntity<Room> entity = this.testRestTemplate.getForEntity(
                hostPathWithPort + "/rooms/id/" + room42.getId(), Room.class
        );

        then(entity.getBody()).
                isEqualToComparingFieldByField(room42);
    }

    @Test
    public void shouldReturnElementRoomsNumberRoute() throws Exception {
        @SuppressWarnings("rawtypes")
        ResponseEntity<Room[]> entity = this.testRestTemplate.getForEntity(
                hostPathWithPort + "/rooms/number/" + room42.getNumber(), Room[].class
        );

        then(entity.getBody()).isNotEmpty();
        then(entity.getBody()).contains(room42);
    }

    @Test
    public void shouldReturnElementRoomsNameRoute() throws Exception {
        @SuppressWarnings("rawtypes")
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> parametersToSend = new LinkedMultiValueMap<>();
        parametersToSend.add("name", room42.getName());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(parametersToSend, headers);

        ResponseEntity<Room[]> entity = this.testRestTemplate.postForEntity(
                hostPathWithPort + "/rooms/name", request, Room[].class
        );

        then(entity.getBody()).isNotEmpty();
        then(entity.getBody()).contains(room42);
    }

}
