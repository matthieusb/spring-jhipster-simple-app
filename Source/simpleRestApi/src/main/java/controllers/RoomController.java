package controllers;


import model.Room;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RestController
public class RoomController {

    private final RoomRepository roomRepository;

    @Autowired
    public RoomController(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }


    @RequestMapping(path = "/rooms", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    List<Room> getAllRooms() {
        return roomRepository.findAll();
    }


    @RequestMapping(path = "/rooms/id/{id}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    ResponseEntity<?> getRoomById(@PathVariable(value="id") String roomIdToSearch) {
        Room roomToReturn = roomRepository.findById(roomIdToSearch);

        if (roomToReturn == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(roomToReturn, HttpStatus.OK);
        }
    }

    @RequestMapping(path = "/rooms/number/{number}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    ResponseEntity<?> getRoomByNumber(@PathVariable(value="number") Integer roomNumberToSearch) {
        List<Room> roomsToReturn = roomRepository.findByNumber(roomNumberToSearch);

        if (roomsToReturn.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(roomsToReturn, HttpStatus.OK);
        }
    }

}
