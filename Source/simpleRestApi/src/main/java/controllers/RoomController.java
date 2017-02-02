package controllers;


import model.Room;
import org.omg.CORBA.Request;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

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

//    @RequestMapping(path = "/rooms/id/{id}", method = RequestMethod.GET, produces = "application/json")
//    public @ResponseBody
//    Room getRoomById(@PathVariable(value="id") String roomIdToSearch) {
//        return roomRepository.findById(roomIdToSearch);
//    }
    @RequestMapping(path = "/rooms/id/{id}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    ResponseEntity<?> getRoomById(@PathVariable(value="id") String roomIdToSearch) {
        Room roomToReturn = roomRepository.findById(roomIdToSearch);
        System.out.print("########### GETTING ROOM");

        if (roomToReturn == null) {
            return new ResponseEntity<>("No room to return", HttpStatus.NO_CONTENT);
        } else {
            System.out.print("########### ROOM GOTTEN : " + roomToReturn);
            return new ResponseEntity<>(roomToReturn, HttpStatus.OK);
        }

    }

    @RequestMapping(path = "/rooms/number/{number}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    List<Room> getRoomByNumber(@PathVariable(value="number") Integer roomNumberToSearch) {
        return roomRepository.findByNumber(roomNumberToSearch);
    }

}
