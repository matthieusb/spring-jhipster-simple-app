package main.aperture.web.rest;

import aperture.config.SpringBootApertureTestingConfiguration;
import aperture.model.Room;
import aperture.model.TestSubject;
import aperture.repository.RoomRepository;
import aperture.repository.TestSubjectRepository;
import aperture.service.impl.TestSubjectServiceImpl;
import aperture.web.rest.TestSubjectResource;
import org.junit.After;
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

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootApertureTestingConfiguration.class)
public class TestSubjectResourceTests {
    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private TestSubjectRepository testSubjectRepository;

    @Autowired
    private TestSubjectServiceImpl testSubjectServiceImpl;

    private MockMvc mockMvc;

    // -- Variables used for tests
    private TestSubject SUBJECT_CAROLINE, SUBJECT_CAROLINE_UPDATED, SUBJECT_WRONG_UPDATED, SUBJECT_NEW;

    @Before
    public void setup() {
        // -- Mock Mvc config
        MockitoAnnotations.initMocks(this);
        TestSubjectResource testSubjectResource = new TestSubjectResource(testSubjectRepository, roomRepository, testSubjectServiceImpl);
        this.mockMvc = MockMvcBuilders.standaloneSetup(testSubjectResource)
            .setMessageConverters(jacksonMessageConverter)
            .build();

        /// -- Personal variables
        List<Room> carolineRooms = new ArrayList<>();
        carolineRooms.add(new Room("5063114bd386d8fadbd6b00d", 1, "Initiation room"));
        carolineRooms.add(new Room("5063114bd386d8fadbd6b00c", 36, "6x6"));
        SUBJECT_CAROLINE = new TestSubject("5063114bd386d8fadbd6b00e", "Caroline", carolineRooms);
        SUBJECT_CAROLINE_UPDATED = new TestSubject("5063114bd386d8fadbd6b00e", "Caroline updated", carolineRooms);
        SUBJECT_WRONG_UPDATED = new TestSubject("5063114bd386d8fadbd6", "Caroline updated", carolineRooms);

        SUBJECT_NEW = new TestSubject("0", "TestSubjectTest", carolineRooms);

        TestUtil.executeAllMongeezScripts();
    }

    @After
    public void putBackInPlace() {
        TestUtil.executeAllMongeezScripts();
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

    // -- Mutability handling operations tests (Create/Delete/Update)
    @Test
    public void should200AndReturnNewTestSubjectWithIdCreateRoute() throws Exception {
        String jsonPathExpression = "$.[?(@.name==\"" + SUBJECT_NEW.getName() + "\")]";
        int databaseSizeBeforeCreate = testSubjectRepository.findAll().size();

        mockMvc.perform(post("/api/subjects/create")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(SUBJECT_NEW))
        )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath(jsonPathExpression).isNotEmpty());

        int databaseSizeAfterCreate = testSubjectRepository.findAll().size();
        assertThat(databaseSizeAfterCreate > databaseSizeBeforeCreate);
    }

    @Test
    public void should400NewTestSubjectWithExistantNameCreateRoute() throws Exception {
        int databaseSizeBeforeCreate = testSubjectRepository.findAll().size();

        mockMvc.perform(post("/api/subjects/create")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(SUBJECT_CAROLINE))
        )
            .andExpect(status().isBadRequest());

        int databaseSizeAfterCreate = testSubjectRepository.findAll().size();
        assertThat(databaseSizeAfterCreate == databaseSizeBeforeCreate);
    }

    @Test
    public void should200AndReturnUpdatedTestSupervisor() throws Exception {
        String jsonPathExpression = "$.[?(@.name==\"" + SUBJECT_CAROLINE_UPDATED.getName() + "\")]";

        mockMvc.perform(put("/api/subjects/update")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(SUBJECT_CAROLINE_UPDATED))
        )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath(jsonPathExpression).isNotEmpty());
    }

    @Test
    public void should400UpdateInexistantTestSupervisor() throws Exception {
        mockMvc.perform(put("/api/subjects/update")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(SUBJECT_WRONG_UPDATED))
        )
            .andExpect(status().isBadRequest());
    }

    @Test
    public void should200AndReturnDeletedTestSubjectWithIdDeleteRoute() throws Exception {
        String jsonPathExpression = "$.[?(@.name==\"" + SUBJECT_CAROLINE.getName() + "\")]";
        int databaseSizeBeforeDelete = testSubjectRepository.findAll().size();

        mockMvc.perform(delete("/api/subjects/delete/" + SUBJECT_CAROLINE.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath(jsonPathExpression).isNotEmpty());

        int databaseSizeAfterDelete = testSubjectRepository.findAll().size();
        assertThat(databaseSizeAfterDelete < databaseSizeBeforeDelete);
    }

    @Test
    public void should400DeleteInexistantTestSubjectDeleteRoute() throws Exception {
        int databaseSizeBeforeDelete = testSubjectRepository.findAll().size();

        mockMvc.perform(delete("/api/subjects/delete/perlimpinpin"))
            .andExpect(status().isBadRequest());

        int databaseSizeAfterDelete = testSubjectRepository.findAll().size();
        assertThat(databaseSizeAfterDelete == databaseSizeBeforeDelete);
    }


}
