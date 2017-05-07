package main.web.rest;

import config.SpringBootApertureTestingConfiguration;
import model.Room;
import model.TestSubject;
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
import repository.TestSubjectRepository;
import web.rest.TestSubjectResource;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootApertureTestingConfiguration.class)
public class TestSubjectResourceTests {
    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private TestSubjectRepository testSubjectRepository;

    private MockMvc mockMvc;

    // -- Variables used for tests
    private TestSubject SUBJECT_CAROLINE;

    @Before
    public void setup() {
        // -- Mock Mvc config
        MockitoAnnotations.initMocks(this);
        TestSubjectResource testSubjectResource = new TestSubjectResource(testSubjectRepository);
        this.mockMvc = MockMvcBuilders.standaloneSetup(testSubjectResource)
            .setMessageConverters(jacksonMessageConverter)
            .build();

        /// -- Personal variables

        List<Room> carolineRooms = new ArrayList<>();
        carolineRooms.add(new Room("5063114bd386d8fadbd6b00d", 1, "Initiation room"));
        carolineRooms.add(new Room("5063114bd386d8fadbd6b00c", 36, "6x6"));

        SUBJECT_CAROLINE = new TestSubject("5063114bd386d8fadbd6b00e", "Caroline", carolineRooms);
    }

    // -- HttpStatus codes tests

    @Test
    public void shouldReturn200SubjectsRoute() throws Exception {
        mockMvc.perform(get("/api/subjects"))
            .andExpect(status().isOk());
    }

    @Test
    public void shouldReturn200SubjectIdFoundRoute() throws Exception {
        mockMvc.perform(get("/api/subjects/id/" + SUBJECT_CAROLINE.getId()))
            .andExpect(status().isOk());
    }

    @Test
    public void shouldReturn204SubjectIdNotFoundRoute() throws Exception {
        mockMvc.perform(get("/api/subjects/id/" + 42))
            .andExpect(status().isNoContent());
    }

    // -- Content returned tests
    @Test
    public void shouldReturnElementsAndCarolineSubjectsRoute() throws Exception {
        String jsonPathExpression = "$.[?(@.id==\"" + SUBJECT_CAROLINE.getId() + "\")]";

        mockMvc.perform(get("/api/subjects"))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isNotEmpty())
            .andExpect(jsonPath(jsonPathExpression).isNotEmpty())
            .andExpect(jsonPath(jsonPathExpression + ".name").value(SUBJECT_CAROLINE.getName()));
    }

    @Test
    public void shouldReturnCarolineSubjectIdRoute() throws Exception {
        String jsonPathExpression = "$.[?(@.id==\"" + SUBJECT_CAROLINE.getId() + "\")]";

        mockMvc.perform(get("/api/subjects/id/" + SUBJECT_CAROLINE.getId()))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isNotEmpty())
            .andExpect(jsonPath(jsonPathExpression).isNotEmpty())
            .andExpect(jsonPath(jsonPathExpression + ".name").value(SUBJECT_CAROLINE.getName()));
    }

    @Test
    public void shouldReturnAtLeastCarolineSubjectNameRoute() throws Exception {
        String jsonPathExpression = "$.[?(@.name==\"" + SUBJECT_CAROLINE.getName() + "\")]";

        mockMvc.perform(post("/api/subjects/name").param("name", SUBJECT_CAROLINE.getName()))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isNotEmpty())
            .andExpect(jsonPath(jsonPathExpression).isNotEmpty())
            .andExpect(jsonPath(jsonPathExpression + ".id").value(SUBJECT_CAROLINE.getId()));
    }


}
