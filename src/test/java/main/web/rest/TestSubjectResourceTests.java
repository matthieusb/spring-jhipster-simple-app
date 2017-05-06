package main.web.rest;

import config.SpringBootApertureTestingConfiguration;
import model.Room;
import model.TestSubject;
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

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootApertureTestingConfiguration.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"management.port=0"})

public class TestSubjectResourceTests {

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
    private HttpHeaders headersFormUrlEncoded;
    private TestSubject carolineSubject;

    @Before
    public void setup() {
        hostPathWithPort = "http://localhost:" + this.port + "/api";
        headersFormUrlEncoded = new HttpHeaders();
        headersFormUrlEncoded.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        List<Room> carolineRooms = new ArrayList<>();
        carolineRooms.add(new Room("5063114bd386d8fadbd6b00d", 1, "Initiation room"));
        carolineRooms.add(new Room("5063114bd386d8fadbd6b00c", 36, "6x6"));

        carolineSubject = new TestSubject("5063114bd386d8fadbd6b00e","Caroline",carolineRooms);
    }

    // -- HttpStatus codes tests

    @Test
    public void shouldReturn200SubjectsRoute() throws Exception {
        @SuppressWarnings("rawtypes")
        ResponseEntity<TestSubject[]> entity = this.testRestTemplate.getForEntity(
                this.hostPathWithPort+ "/subjects", TestSubject[].class
        );

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void shouldReturn200SubjectIdFoundRoute() throws Exception {
        @SuppressWarnings("rawtypes")
        ResponseEntity<TestSubject> entity = this.testRestTemplate.getForEntity(
            this.hostPathWithPort + "/subjects/id/" + carolineSubject.getId(), TestSubject.class
        );

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void shouldReturn204SubjectIdNotFoundRoute() throws Exception {
        @SuppressWarnings("rawtypes")
        ResponseEntity<TestSubject> entity = this.testRestTemplate.getForEntity(
                this.hostPathWithPort + "/subjects/id/" + 42, TestSubject.class
        );

        then(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }


    // -- Content returned tests
    @Test
    public void shouldReturnElementsAndCarolineSubjectsRoute() {
        @SuppressWarnings("rawtypes")
        ResponseEntity<TestSubject[]> entity = this.testRestTemplate.getForEntity(
            this.hostPathWithPort + "/subjects", TestSubject[].class
        );

        then(entity.getBody()).isNotEmpty();
        then(entity.getBody()).contains(carolineSubject);
    }

    @Test
    public void shouldReturnCarolineSubjectIdRoute() {
        @SuppressWarnings("rawtypes")
        ResponseEntity<TestSubject> entity = this.testRestTemplate.getForEntity(
                this.hostPathWithPort + "/subjects/id/" + carolineSubject.getId(), TestSubject.class
        );

        then(entity.getBody()).isEqualTo(carolineSubject);
    }

    @Test
    public void shouldReturnAtLeastCarolineSubjectNameRoute() {
        @SuppressWarnings("rawtypes")

        MultiValueMap<String, String> paramatersToSend = new LinkedMultiValueMap<>();
        paramatersToSend.add("name", this.carolineSubject.getName());


        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(paramatersToSend, this.headersFormUrlEncoded);
        ResponseEntity<TestSubject[]> entity = this.testRestTemplate.postForEntity(
                hostPathWithPort + "/subjects/name", request, TestSubject[].class
        );

        then(entity.getBody()).isNotEmpty();
        then(entity.getBody()).contains(this.carolineSubject);
    }


}
