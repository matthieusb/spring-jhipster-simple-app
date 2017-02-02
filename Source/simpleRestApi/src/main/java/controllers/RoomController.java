package controllers;


import model.Room;
import org.omg.CORBA.Request;
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

    @RequestMapping(path = "/rooms/getById/{id}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    Room getRoomById(@PathVariable(value="id") String roomIdToSearch) {
        return roomRepository.findById(roomIdToSearch);
    }

    @RequestMapping(path = "/rooms/getByNumber/{number}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    List<Room> getRoomByNumber(@PathVariable(value="number") Integer roomNumberToSearch) {
        return roomRepository.findByNumber(roomNumberToSearch);
    }



}
