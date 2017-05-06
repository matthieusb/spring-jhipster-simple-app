package web.rest;


import model.Room;
import web.rest.util.ResponseEntityUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomResource {

    private final RoomRepository roomRepository;

    @Autowired
    public RoomResource(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }


    @GetMapping(produces = "application/json")
    public @ResponseBody
    ResponseEntity<List<Room>> getAllRooms() {
        List<Room> roomsFound = roomRepository.findAll();
        if (roomsFound.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok().body(roomsFound);
        }
    }

    @GetMapping(path = "/id/{id}", produces = "application/json")
    public @ResponseBody
    ResponseEntity<?> getRoomById(@PathVariable(value = "id") String roomIdToSearch) {
        return ResponseEntityUtils.
                getResponseEntityForSingleResponse(roomRepository.findById(roomIdToSearch));
    }

    @GetMapping(path = "/number/{number}", produces = "application/json")
    public @ResponseBody
    ResponseEntity<?> getRoomByNumber(@PathVariable(value = "number") Integer roomNumberToSearch) {
        return ResponseEntityUtils.
                getResponseEntityForMultipleResponses(roomRepository.findByNumber(roomNumberToSearch));
    }

    @PostMapping(path = "/name", produces = "application/json")
    public @ResponseBody
    ResponseEntity<?> getRoomByName(@RequestParam(value = "name") String roomNameToSearch) {
        return ResponseEntityUtils.
                getResponseEntityForMultipleResponses(roomRepository.findByName(roomNameToSearch));
    }
}
