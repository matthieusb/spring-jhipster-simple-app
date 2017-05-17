package test.aperture.web.rest;


import aperture.config.SpringBootApertureTestingConfiguration;
import aperture.model.Room;
import aperture.repository.RoomRepository;
import aperture.service.RoomService;
import aperture.web.rest.RoomResource;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootApertureTestingConfiguration.class)
public class RoomResourceTests {
    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private RoomService roomService;

    @Autowired
    private RoomRepository roomRepository;

    private MockMvc mockMvc;

    // -- Variables used for tests
    private static final int FOURTYTWO = 42;
    private static final int EIGHT = 8888;

    private Room room42 = new Room("5063114bd386d8fadbd6b00a",
        FOURTYTWO,
        "Answer to life room"
    );
    private Room room42Updated = new Room("5063114bd386d8fadbd6b00a",
        FOURTYTWO,
        "Answer to life room updated"
    );
    private Room roomWrongUpdated = new Room("5063114bd386d8fadb",
        FOURTYTWO,
        "WRONG ROOM"
    );
    private Room newRoom = new Room("0",
        EIGHT,
        "ADD ROOM TEST"
    );

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        RoomResource roomResource = new RoomResource(roomService, roomRepository);

        this.mockMvc = MockMvcBuilders.standaloneSetup(roomResource)
            .setMessageConverters(jacksonMessageConverter)
            .build();

        TestUtil.executeAllMongeezScripts();
    }

    // -- HttpStatus codes tests

    @Test
    public void shouldReturn200RoomsRoute() throws Exception {
        mockMvc.perform(get("/api/rooms"))
            .andExpect(status().isOk());
    }

    @Test
    public void shouldReturn200RoomsIdFoundRoute() throws Exception {
        mockMvc.perform(get("/api/rooms/id/" + room42.getId()))
            .andExpect(status().isOk());
    }

    @Test
    public void shouldReturn204RoomsIdNotFoundRoute() throws Exception {
        mockMvc.perform(get("/api/rooms/id/42"))
            .andExpect(status().isNoContent());
    }

    // -- Content returned tests

    @Test
    public void shouldReturnElementsAndRoom42RoomsRoute() throws Exception {
        String jsonPathExpression = "$.[?(@.id==\"" + room42.getId() + "\")]";

        mockMvc.perform(get("/api/rooms"))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isNotEmpty())
            .andExpect(jsonPath(jsonPathExpression).isNotEmpty())
            .andExpect(jsonPath(jsonPathExpression + ".name").value(room42.getName()))
            .andExpect(jsonPath(jsonPathExpression + ".number").value(room42.getNumber()));
    }

    @Test
    public void shouldReturnElementRoomsIdRoute() throws Exception {
        String jsonPathExpression = "$.[?(@.id==\"" + room42.getId() + "\")]";

        mockMvc.perform(get("/api/rooms/id/" + room42.getId()))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isNotEmpty())
            .andExpect(jsonPath(jsonPathExpression).isNotEmpty())
            .andExpect(jsonPath(jsonPathExpression + ".name").value(room42.getName()))
            .andExpect(jsonPath(jsonPathExpression + ".number").value(room42.getNumber()));
    }

    @Test
    public void shouldReturnElementRoomsNumberRoute() throws Exception {
        String jsonPathExpression = "$.[?(@.number==\"" + room42.getNumber() + "\")]";

        mockMvc.perform(get("/api/rooms/number/" + room42.getNumber()))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isNotEmpty())
            .andExpect(jsonPath(jsonPathExpression).isNotEmpty())
            .andExpect(jsonPath(jsonPathExpression + ".name").value(room42.getName()))
            .andExpect(jsonPath(jsonPathExpression + ".id").value(room42.getId()));
    }

    @Test
    public void shouldReturnElementRoomsNameRoute() throws Exception {
        String jsonPathExpression = "$.[?(@.name==\"" + room42.getName() + "\")]";

        mockMvc.perform(post("/api/rooms/name").param("name", room42.getName()))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isNotEmpty())
            .andExpect(jsonPath(jsonPathExpression).isNotEmpty())
            .andExpect(jsonPath(jsonPathExpression + ".name").value(room42.getName()))
            .andExpect(jsonPath(jsonPathExpression + ".id").value(room42.getId()));
    }

    // -- Mutability handling operations tests (Create/Delete/Update)

    @Test
    public void should200AndReturnNewRoomWithIdCreateRoute() throws Exception {
        String jsonPathExpression = "$.[?(@.name==\"" + newRoom.getName() + "\")]";
        int databaseSizeBeforeCreate = roomRepository.findAll().size();

        mockMvc.perform(post("/api/rooms/create")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(newRoom))
        )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath(jsonPathExpression).isNotEmpty());

        int databaseSizeAfterCreate = roomRepository.findAll().size();
        assertThat(databaseSizeAfterCreate > databaseSizeBeforeCreate);
    }

    @Test
    public void should400NewRoomWithExistantNumberCreateRoute() throws Exception {
        int databaseSizeBeforeCreate = roomRepository.findAll().size();

        mockMvc.perform(post("/api/rooms/create")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(room42))
        )
            .andExpect(status().isBadRequest());

        int databaseSizeAfterCreate = roomRepository.findAll().size();
        assertThat(databaseSizeAfterCreate == databaseSizeBeforeCreate);
    }

    @Test
    public void should200AndReturnUpdatedRoom() throws Exception {
        String jsonPathExpression = "$.[?(@.name==\"" + room42Updated.getName() + "\")]";

        mockMvc.perform(put("/api/rooms/update")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(room42Updated))
        )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath(jsonPathExpression).isNotEmpty());
    }

    @Test
    public void should400UpdateInexistantRoom() throws Exception {
        mockMvc.perform(put("/api/rooms/update")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roomWrongUpdated))
        )
            .andExpect(status().isBadRequest());
    }

    @Test
    public void should200AndReturnDeletedRoomWithIdDeleteRoute() throws Exception {
        String jsonPathExpression = "$.[?(@.name==\"" + room42.getName() + "\")]";
        int databaseSizeBeforeDelete = roomRepository.findAll().size();

        mockMvc.perform(delete("/api/rooms/delete/" + room42.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath(jsonPathExpression).isNotEmpty());

        int databaseSizeAfterDelete = roomRepository.findAll().size();
        assertThat(databaseSizeAfterDelete < databaseSizeBeforeDelete);
    }

    @Test
    public void should400DeleteInexistantRoomDeleteRoute() throws Exception {
        int databaseSizeBeforeDelete = roomRepository.findAll().size();

        mockMvc.perform(delete("/api/rooms/delete/perlimpinpin"))
            .andExpect(status().isBadRequest());

        int databaseSizeAfterDelete = roomRepository.findAll().size();
        assertThat(databaseSizeAfterDelete == databaseSizeBeforeDelete);
    }

}
