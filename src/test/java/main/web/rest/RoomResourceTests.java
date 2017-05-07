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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

}
