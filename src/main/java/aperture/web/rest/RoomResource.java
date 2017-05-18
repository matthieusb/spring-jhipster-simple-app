package aperture.web.rest;


import aperture.model.Room;
import aperture.model.enums.TypeOperation;
import aperture.repository.RoomRepository;
import aperture.service.RoomService;
import aperture.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Resource containing all web rest operations for Rooms.
 */
@RestController
@RequestMapping(value = "/api/rooms", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
public class RoomResource {

    private final Logger logger = LoggerFactory.getLogger(RoomResource.class);

    private final RoomService roomService;

    private final RoomRepository roomRepository;

    public RoomResource(RoomService roomServiceToSet, RoomRepository roomRepositoryToSet) {
        this.roomService = roomServiceToSet;
        this.roomRepository = roomRepositoryToSet;
    }

    @GetMapping
    public @ResponseBody
    ResponseEntity<List<Room>> getAllRooms() {
        logger.info("REST request to getAllRooms()");

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
        logger.info("REST request to getRoomById() : " + roomIdToSearch);

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
        logger.debug("REST request to getRoomByNumber() : " + roomNumberToSearch);

        Room room = roomRepository.findByNumber(roomNumberToSearch);
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
        logger.info("REST request to getRoomByName() : " + roomNameToSearch);

        List<Room> rooms = roomRepository.findByName(roomNameToSearch);
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
        logger.info("REST request to createRoom() : " + roomToCreate);
        Room roomOutput = roomService.createOrUpdateRoom(roomToCreate, TypeOperation.CREATE);

        if (roomOutput != null) {
            return ResponseEntity.ok()
                .headers(HeaderUtil.getStandardHeaders())
                .body(roomOutput);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping(path = "/update")
    public @ResponseBody
    ResponseEntity<Room> updateRoom(@RequestBody Room roomToUpdate) {
        logger.info("REST request to updateRoom() : " + roomToUpdate);
        Room roomOutput = roomService.createOrUpdateRoom(roomToUpdate, TypeOperation.UPDATE);

        if (roomOutput != null) {
            return ResponseEntity.ok()
                .headers(HeaderUtil.getStandardHeaders())
                .body(roomOutput);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping(path = "/delete/{id}")
    public @ResponseBody
    ResponseEntity<Room> deleteRoom(@PathVariable(value = "id") String idRoomToDelete) {
        logger.info("REST request to deleteRoom : " + idRoomToDelete);

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
