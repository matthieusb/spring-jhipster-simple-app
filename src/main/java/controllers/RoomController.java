package controllers;


import controllers.utils.ResponseEntityUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;

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
        return ResponseEntityUtils.
                getResponseEntityForMultipleResponses(roomRepository.findAll());
    }

    @GetMapping(path = "/rooms/id/{id}", produces = "application/json")
    public @ResponseBody
    ResponseEntity<?> getRoomById(@PathVariable(value = "id") String roomIdToSearch) {
        return ResponseEntityUtils.
                getResponseEntityForSingleResponse(roomRepository.findById(roomIdToSearch));
    }

    @GetMapping(path = "/rooms/number/{number}", produces = "application/json")
    public @ResponseBody
    ResponseEntity<?> getRoomByNumber(@PathVariable(value = "number") Integer roomNumberToSearch) {
        return ResponseEntityUtils.
                getResponseEntityForMultipleResponses(roomRepository.findByNumber(roomNumberToSearch));
    }

    @PostMapping(path = "/rooms/name", produces = "application/json")
    public @ResponseBody
    ResponseEntity<?> getRoomByName(@RequestParam(value = "name") String roomNameToSearch) {
        return ResponseEntityUtils.
                getResponseEntityForMultipleResponses(roomRepository.findByName(roomNameToSearch));
    }
}
