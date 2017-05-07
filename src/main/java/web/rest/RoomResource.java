package web.rest;


import model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.RoomRepository;
import web.rest.util.ResponseEntityUtils;

import java.util.List;

@RestController
@RequestMapping(value = "/api/rooms", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
public class RoomResource {

    private final RoomRepository roomRepository;

    @Autowired
    public RoomResource(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }


    @GetMapping()
    public @ResponseBody
    ResponseEntity<List<Room>> getAllRooms() {
        List<Room> roomsFound = roomRepository.findAll();
        if (roomsFound.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok()
                .headers(ResponseEntityUtils.getStandardHeaders())
                .body(roomsFound);
        }
    }

    @GetMapping(path = "/id/{id}", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public @ResponseBody
    ResponseEntity<Room> getRoomById(@PathVariable(value = "id") String roomIdToSearch) {
        Room room = roomRepository.findById(roomIdToSearch);
        if (room == null) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok()
                .headers(ResponseEntityUtils.getStandardHeaders())
                .body(room);
        }
    }

    @GetMapping(path = "/number/{number}")
    public @ResponseBody
    ResponseEntity<Room> getRoomByNumber(@PathVariable(value = "number") Integer roomNumberToSearch) {
        List<Room> rooms = roomRepository.findByNumber(roomNumberToSearch);
        //TODO Add error case when several rooms ?
        if (rooms.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok()
                .headers(ResponseEntityUtils.getStandardHeaders())
                .body(rooms.get(0));
        }
    }

    @PostMapping(path = "/name")
    public @ResponseBody
    ResponseEntity<Room> getRoomByName(@RequestParam(value = "name") String roomNameToSearch) {
        List<Room> rooms = roomRepository.findByName(roomNameToSearch);
        //TODO Add error case when several rooms ?
        if (rooms.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok()
                .headers(ResponseEntityUtils.getStandardHeaders())
                .body(rooms.get(0));
        }
    }
}
