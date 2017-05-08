package main.web.rest;


import config.SpringBootApertureTestingConfiguration;
import model.Room;
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
import repository.RoomRepository;
import web.rest.RoomResource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootApertureTestingConfiguration.class)
public class RoomResourceTests {
    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private RoomRepository roomRepository;

    private MockMvc mockMvc;

    // -- Variables used for tests
    private Room ROOM_42 = new Room("5063114bd386d8fadbd6b00a", 42, "Answer to life room");
    private Room ROOM_42_UPDATED = new Room("5063114bd386d8fadbd6b00a", 42, "Answer to life room updated");
    private Room ROOM_WRONG_UPDATED = new Room("5063114bd386d8fadb", 44, "WRONG ROOM");
    private Room NEW_ROOM = new Room("0", 8888, "ADD ROOM TEST");

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        RoomResource roomResource = new RoomResource(roomRepository);

        this.mockMvc = MockMvcBuilders.standaloneSetup(roomResource)
            .setMessageConverters(jacksonMessageConverter)
            .build();
    }

    // -- HttpStatus codes tests

    @Test
    public void shouldReturn200RoomsRoute() throws Exception {
        mockMvc.perform(get("/api/rooms"))
            .andExpect(status().isOk());
    }

    @Test
    public void shouldReturn200RoomsIdFoundRoute() throws Exception {
        mockMvc.perform(get("/api/rooms/id/" + ROOM_42.getId()))
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
        String jsonPathExpression = "$.[?(@.id==\"" + ROOM_42.getId() + "\")]";

        mockMvc.perform(get("/api/rooms"))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isNotEmpty())
            .andExpect(jsonPath(jsonPathExpression).isNotEmpty())
            .andExpect(jsonPath(jsonPathExpression + ".name").value(ROOM_42.getName()))
            .andExpect(jsonPath(jsonPathExpression + ".number").value(ROOM_42.getNumber()));
    }

    @Test
    public void shouldReturnElementRoomsIdRoute() throws Exception {
        String jsonPathExpression = "$.[?(@.id==\"" + ROOM_42.getId() + "\")]";

        mockMvc.perform(get("/api/rooms/id/" + ROOM_42.getId()))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isNotEmpty())
            .andExpect(jsonPath(jsonPathExpression).isNotEmpty())
            .andExpect(jsonPath(jsonPathExpression + ".name").value(ROOM_42.getName()))
            .andExpect(jsonPath(jsonPathExpression + ".number").value(ROOM_42.getNumber()));
    }

    @Test
    public void shouldReturnElementRoomsNumberRoute() throws Exception {
        String jsonPathExpression = "$.[?(@.number==\"" + ROOM_42.getNumber() + "\")]";

        mockMvc.perform(get("/api/rooms/number/" + ROOM_42.getNumber()))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isNotEmpty())
            .andExpect(jsonPath(jsonPathExpression).isNotEmpty())
            .andExpect(jsonPath(jsonPathExpression + ".name").value(ROOM_42.getName()))
            .andExpect(jsonPath(jsonPathExpression + ".id").value(ROOM_42.getId()));
    }

    @Test
    public void shouldReturnElementRoomsNameRoute() throws Exception {
        String jsonPathExpression = "$.[?(@.name==\"" + ROOM_42.getName() + "\")]";

        mockMvc.perform(post("/api/rooms/name").param("name", ROOM_42.getName()))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isNotEmpty())
            .andExpect(jsonPath(jsonPathExpression).isNotEmpty())
            .andExpect(jsonPath(jsonPathExpression + ".name").value(ROOM_42.getName()))
            .andExpect(jsonPath(jsonPathExpression + ".id").value(ROOM_42.getId()));
    }

    // -- Mutability handling operations tests (Create/Delete/Update)

    @Test
    public void should200AndReturnNewRoomWithIdCreateRoute() throws Exception {
        String jsonPathExpression = "$.[?(@.name==\"" + NEW_ROOM.getName() + "\")]";
        int databaseSizeBeforeCreate = roomRepository.findAll().size();

        mockMvc.perform(post("/api/rooms/create")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(NEW_ROOM))
        )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath(jsonPathExpression).isNotEmpty());

        int databaseSizeAfterCreate = roomRepository.findAll().size();
        assertThat(databaseSizeAfterCreate > databaseSizeBeforeCreate);

        roomRepository.delete(roomRepository.findByNumber(NEW_ROOM.getNumber()));
    }

    @Test
    public void should400NewRoomWithExistantNumberCreateRoute() throws Exception {
        int databaseSizeBeforeCreate = roomRepository.findAll().size();

        mockMvc.perform(post("/api/rooms/create")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ROOM_42))
        )
            .andExpect(status().isBadRequest());

        int databaseSizeAfterCreate = roomRepository.findAll().size();
        assertThat(databaseSizeAfterCreate == databaseSizeBeforeCreate);
    }

    @Test
    public void should200AndReturnUpdatedRoom() throws Exception {
        String jsonPathExpression = "$.[?(@.name==\"" + ROOM_42_UPDATED.getName() + "\")]";

        mockMvc.perform(put("/api/rooms/update")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ROOM_42_UPDATED))
        )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath(jsonPathExpression).isNotEmpty());

        roomRepository.save(ROOM_42); // Put back to default
    }

    @Test
    public void should400UpdateInexistantRoom() throws Exception {
        mockMvc.perform(put("/api/rooms/update")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ROOM_WRONG_UPDATED))
        )
            .andExpect(status().isBadRequest());
    }

    @Test
    public void should200AndReturnDeletedRoomWithIdDeleteRoute() throws Exception {
        String jsonPathExpression = "$.[?(@.name==\"" + NEW_ROOM.getName() + "\")]";

        Room roomToDelete = roomRepository.save(NEW_ROOM);
        int databaseSizeBeforeDelete = roomRepository.findAll().size();

        if (roomToDelete == null) {
            fail("The NEW Room to delete was not found by number : " + NEW_ROOM);
        } else {
            mockMvc.perform(delete("/api/rooms/delete/" + roomToDelete.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath(jsonPathExpression).isNotEmpty());
        }

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
