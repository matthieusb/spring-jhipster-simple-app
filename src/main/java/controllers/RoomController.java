package controllers;


import controllers.common.ResponseEntityOperations;
import model.Room;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.xml.ws.Response;
import java.util.List;

@RestController
public class RoomController {

    private final RoomRepository roomRepository;

    @Autowired
    public RoomController(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }


    @GetMapping(path = "/rooms", produces = "application/json")
    public @ResponseBody
    ResponseEntity<?> getAllRooms() {
        return ResponseEntityOperations.
                getResponseEntityForMultipleResponses(roomRepository.findAll());
    }

    @GetMapping(path = "/rooms/id/{id}", produces = "application/json")
    public @ResponseBody
    ResponseEntity<?> getRoomById(@PathVariable(value = "id") String roomIdToSearch) {
        return ResponseEntityOperations.
                getResponseEntityForSingleResponse(roomRepository.findById(roomIdToSearch));
    }

    @GetMapping(path = "/rooms/number/{number}", produces = "application/json")
    public @ResponseBody
    ResponseEntity<?> getRoomByNumber(@PathVariable(value = "number") Integer roomNumberToSearch) {
        return ResponseEntityOperations.
                getResponseEntityForMultipleResponses(roomRepository.findByNumber(roomNumberToSearch));
    }

    @PostMapping(path = "/rooms/name", produces = "application/json")
    public @ResponseBody
    ResponseEntity<?> getRoomByName(@RequestParam(value = "name") String roomNameToSearch) {
        return ResponseEntityOperations.
                getResponseEntityForMultipleResponses(roomRepository.findByName(roomNameToSearch));
    }
}
