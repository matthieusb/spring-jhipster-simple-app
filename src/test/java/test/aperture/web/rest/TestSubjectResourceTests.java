package test.aperture.web.rest;

import aperture.config.SpringBootApertureApiConfiguration;
import aperture.model.Room;
import aperture.model.TestSubject;
import aperture.repository.RoomRepository;
import aperture.repository.TestSubjectRepository;
import aperture.service.TestSubjectService;
import aperture.web.rest.TestSubjectResource;
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

import static junit.framework.TestCase.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Tests on TestSubject rest resource.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootApertureApiConfiguration.class)
public class TestSubjectResourceTests {
    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private TestSubjectRepository testSubjectRepository;

    @Autowired
    private TestSubjectService testSubjectService;

    @Autowired
    private RoomRepository roomRepository;

    private MockMvc mockMvc;

    // -- Variables used for tests
    private static final int FOURTYTWO = 42;
    private static final int THIRTYSIX = 36;

    private TestSubject subjectCaroline, subjectCarolineUpdated, subjectWrongUpdated, subjectPBody, subjectNew;

    private Room initRoomToUpdate = new Room("5063114bd386d8fadbd6b00d",
        1,
        "Initiation room"
    );

    @Before
    public void setup() {
        // -- Mock Mvc config
        MockitoAnnotations.initMocks(this);
        TestSubjectResource testSubjectResource = new TestSubjectResource(
            testSubjectRepository, testSubjectService
        );
        this.mockMvc = MockMvcBuilders.standaloneSetup(testSubjectResource)
            .setMessageConverters(jacksonMessageConverter)
            .build();

        /// -- Personal variables
        List<Room> carolineRooms = new ArrayList<>();
        carolineRooms.add(new Room("5063114bd386d8fadbd6b00d", 1, "Initiation room"));
        carolineRooms.add(new Room("5063114bd386d8fadbd6b00c", THIRTYSIX, "6x6"));

        List<Room> pBodyRooms = new ArrayList<>();
        pBodyRooms.add(new Room("5063114bd386d8fadbd6b00d", 1, "Initiation room"));
        pBodyRooms.add(new Room("5063114bd386d8fadbd6b01a", 404, "Room Not found"));

        subjectCaroline = new TestSubject("5063114bd386d8fadbd6b00e", "Caroline", carolineRooms);
        subjectCarolineUpdated = new TestSubject("5063114bd386d8fadbd6b00e", "Caroline updated", carolineRooms);
        subjectWrongUpdated = new TestSubject("5063114bd386d8fadbd6", "Caroline updated", carolineRooms);
        subjectPBody = new TestSubject("5063114bd386d8fadbd6b01a", "P-Body", pBodyRooms);
        subjectNew = new TestSubject("0", "TestSubjectTest", carolineRooms);


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
        mockMvc.perform(get("/api/subjects/id/" + subjectCaroline.getId()))
            .andExpect(status().isOk());
    }

    @Test
    public void shouldReturn204SubjectIdNotFoundRoute() throws Exception {
        mockMvc.perform(get("/api/subjects/id/" + FOURTYTWO))
            .andExpect(status().isNoContent());
    }

    // -- Content returned tests
    @Test
    public void shouldReturnElementsAndCarolineSubjectsRoute() throws Exception {
        String jsonPathExpression = "$.[?(@.id==\"" + subjectCaroline.getId() + "\")]";

        mockMvc.perform(get("/api/subjects"))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isNotEmpty())
            .andExpect(jsonPath(jsonPathExpression).isNotEmpty())
            .andExpect(jsonPath(jsonPathExpression + ".name").value(subjectCaroline.getName()));
    }

    @Test
    public void shouldReturnCarolineSubjectIdRoute() throws Exception {
        String jsonPathExpression = "$.[?(@.id==\"" + subjectCaroline.getId() + "\")]";

        mockMvc.perform(get("/api/subjects/id/" + subjectCaroline.getId()))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isNotEmpty())
            .andExpect(jsonPath(jsonPathExpression).isNotEmpty())
            .andExpect(jsonPath(jsonPathExpression + ".name").value(subjectCaroline.getName()));
    }

    @Test
    public void shouldReturnAtLeastCarolineSubjectNameRoute() throws Exception {
        String jsonPathExpression = "$.[?(@.name==\"" + subjectCaroline.getName() + "\")]";

        mockMvc.perform(post("/api/subjects/name").param("name", subjectCaroline.getName()))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isNotEmpty())
            .andExpect(jsonPath(jsonPathExpression).isNotEmpty())
            .andExpect(jsonPath(jsonPathExpression + ".id").value(subjectCaroline.getId()));
    }

    // -- Mutability handling operations tests (Create/Delete/Update)

    @Test
    public void should200AndReturnNewTestSubjectWithIdCreateRoute() throws Exception {
        String jsonPathExpression = "$.[?(@.name==\"" + subjectNew.getName() + "\")]";
        int databaseSizeBeforeCreate = testSubjectRepository.findAll().size();

        mockMvc.perform(post("/api/subjects/create")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subjectNew))
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
            .content(TestUtil.convertObjectToJsonBytes(subjectCaroline))
        )
            .andExpect(status().isBadRequest());

        int databaseSizeAfterCreate = testSubjectRepository.findAll().size();
        assertThat(databaseSizeAfterCreate == databaseSizeBeforeCreate);
    }

    @Test
    public void should200AndReturnUpdatedTestSupervisor() throws Exception {
        String jsonPathExpression = "$.[?(@.name==\"" + subjectCarolineUpdated.getName() + "\")]";

        mockMvc.perform(put("/api/subjects/update")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subjectCarolineUpdated))
        )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath(jsonPathExpression).isNotEmpty());
    }

    @Test
    public void should400UpdateInexistantTestSupervisor() throws Exception {
        mockMvc.perform(put("/api/subjects/update")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subjectWrongUpdated))
        )
            .andExpect(status().isBadRequest());
    }

    @Test
    public void should200AndReturnDeletedTestSubjectWithIdDeleteRoute() throws Exception {
        String jsonPathExpression = "$.[?(@.name==\"" + subjectCaroline.getName() + "\")]";
        int databaseSizeBeforeDelete = testSubjectRepository.findAll().size();

        mockMvc.perform(delete("/api/subjects/delete/" + subjectCaroline.getId()))
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

    // -- Async room update methods
    @Test
    public void should202AndUpdateSpecificSubjectRoomTriggerRoomsUpdate() throws Exception {
        this.roomRepository.save(initRoomToUpdate); //First, updarte this room to change its name

        mockMvc.perform(get("/api/subjects/update/rooms/" + subjectCaroline.getId()))
            .andExpect(status().isAccepted());

        TestSubject testSubjectCarolineUpdated = this.testSubjectRepository.findById(subjectCaroline.getId());
        assertTrue(//The room should have been correctly udated in caroline
            testSubjectCarolineUpdated.getRooms().stream().anyMatch(
                room -> room.getName().equals(initRoomToUpdate.getName())
            )
        );
    }

    @Test
    public void should202AndDeleteSpecificSubjectRoomTriggerRoomsUpdate() throws Exception {
        this.roomRepository.deleteById(initRoomToUpdate.getId()); //First, delete this room

        mockMvc.perform(get("/api/subjects/update/rooms/" + subjectCaroline.getId()))
            .andExpect(status().isAccepted());

        TestSubject subjectCarolineAfterUpdate = this.testSubjectRepository.findById(subjectCaroline.getId());
        assertTrue(//The room should have been correctly deleted
            subjectCarolineAfterUpdate.getRooms().size()
                == this.subjectCaroline.getRooms().size() - 1
        );
    }

    @Test
    public void should400AndWithIncorrectIdTriggerRoomsUpdate() throws Exception {
        mockMvc.perform(get("/api/subjects/update/rooms/idInexistant"))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void should202AndUpdateAllSubjectsSubjectRoomsTriggerRoomsUpdateForAllSubjects() throws Exception {
        this.roomRepository.save(initRoomToUpdate); //First, update this room to change its name

        mockMvc.perform(get("/api/subjects/update/rooms/all"))
            .andExpect(status().isAccepted());

        TestSubject subjectCarolineAfterUpdate = this.testSubjectRepository.findById(subjectCaroline.getId());
        TestSubject subjectPBodyAfterUpdate = this.testSubjectRepository.findById(subjectPBody.getId());
        assertTrue(subjectCarolineAfterUpdate.getRooms()//The room should have been correctly updated in caroline
            .stream().anyMatch(room -> room.getName().equals(initRoomToUpdate.getName()))
        );
        assertTrue(subjectPBodyAfterUpdate //The room should have been correctly updated in p-body
            .getRooms().stream().anyMatch(room -> room.getName().equals(initRoomToUpdate.getName())));
    }

    @Test
    public void should202AndDeleteRoomsForAllSubjectsSubjectRoomsTriggerRoomsUpdateForAllSubjects() throws Exception {
        this.roomRepository.deleteById(initRoomToUpdate.getId()); //First, delete this room

        mockMvc.perform(get("/api/subjects/update/rooms/all"))
            .andExpect(status().isAccepted());

        TestSubject subjectCarolineAfterUpdate = this.testSubjectRepository.findById(subjectCaroline.getId());
        TestSubject subjectPBodyAfterUpdate = this.testSubjectRepository.findById(subjectPBody.getId());

        assertTrue(//The room should have been correctly deleted
            subjectCarolineAfterUpdate.getRooms().size()
                < this.subjectCaroline.getRooms().size()
        );
        assertTrue(//The room should have been correctly deleted
            subjectPBodyAfterUpdate.getRooms().size()
                < this.subjectPBody.getRooms().size()
        );
    }
}
