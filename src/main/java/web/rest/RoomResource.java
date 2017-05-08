package web.rest;


import model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.RoomRepository;
import web.rest.util.HeaderUtil;

import java.util.List;

@RestController
@RequestMapping(value = "/api/rooms", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
public class RoomResource {

    private final RoomRepository roomRepository;

    @Autowired
    public RoomResource(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }


    @GetMapping
    public @ResponseBody
    ResponseEntity<List<Room>> getAllRooms() {
        List<Room> roomsFound = roomRepository.findAll();
        if (roomsFound.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok()
                .headers(HeaderUtil.getStandardHeaders())
                .body(roomsFound);
        }
    }

    @GetMapping(path = "/id/{id}")
    public @ResponseBody
    ResponseEntity<Room> getRoomById(@PathVariable(value = "id") String roomIdToSearch) {
        Room room = roomRepository.findById(roomIdToSearch);
        if (room == null) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok()
                .headers(HeaderUtil.getStandardHeaders())
                .body(room);
        }
    }

    @GetMapping(path = "/number/{number}")
    public @ResponseBody
    ResponseEntity<Room> getRoomByNumber(@PathVariable(value = "number") Integer roomNumberToSearch) {
        Room room = roomRepository.findByNumber(roomNumberToSearch);
        //TODO Add error case when several rooms ?
        if (room == null) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok()
                .headers(HeaderUtil.getStandardHeaders())
                .body(room);
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
                .headers(HeaderUtil.getStandardHeaders())
                .body(rooms.get(0));
        }
    }

    @PostMapping(path = "/create")
    public @ResponseBody
    ResponseEntity<Room> createRoom(@RequestBody Room roomToCreate) {
        if (roomToCreate != null) {
            roomToCreate.setId(null);
            if (roomRepository.findByNumber(roomToCreate.getNumber()) != null) {
                return ResponseEntity.badRequest().body(null);
            } else {
                Room roomOutput = roomRepository.save(roomToCreate);
                return ResponseEntity.ok()
                    .headers(HeaderUtil.getStandardHeaders())
                    .body(roomOutput);
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping(path = "/delete/{id}")
    public @ResponseBody
    ResponseEntity<Room> deleteRoom(@PathVariable(value = "id") String idRoomToDelete) {
        Room roomToDelete = roomRepository.findById(idRoomToDelete);

        if (roomToDelete == null) {
            return ResponseEntity.badRequest().body(null);
        } else {
            roomRepository.delete(roomToDelete);
            return ResponseEntity.ok()
                .headers(HeaderUtil.getStandardHeaders())
                .body(roomToDelete);
        }
    }
}
