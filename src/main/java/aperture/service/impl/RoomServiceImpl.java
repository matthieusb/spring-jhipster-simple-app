package aperture.service.impl;

import aperture.model.Room;
import aperture.model.enums.TypeOperation;
import aperture.repository.RoomRepository;
import aperture.service.RoomService;
import org.springframework.stereotype.Service;

/**
 * Service containing all complex operations for rooms on database.
 */
@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    public RoomServiceImpl(RoomRepository roomRepositoryToSet) {
        this.roomRepository = roomRepositoryToSet;
    }

    @Override
    public Room createOrUpdateRoom(Room roomToCreateOrUpdate, TypeOperation typeOperation) {
        if (roomToCreateOrUpdate == null) {
            return null;
        } else {
            Room roomToReturn = null;
            if (typeOperation == TypeOperation.CREATE) {
                roomToReturn = createRoom(roomToCreateOrUpdate);
            } else if (typeOperation == TypeOperation.UPDATE) {
                roomToReturn = updateRoom(roomToCreateOrUpdate);
            }
            return roomToReturn;
        }
    }

    @Override
    public Room createRoom(Room roomToCreate) {
        roomToCreate.setId(null);
        if (roomRepository.findByNumber(roomToCreate.getNumber()) != null) {
            return null;
        } else {
            return roomRepository.save(roomToCreate);
        }
    }

    @Override
    public Room updateRoom(Room roomToUpdate) {
        if (roomRepository.findById(roomToUpdate.getId()) == null) {
            return null;
        } else {
            Room roomToReturn = null;
            Room roomFoundByNumber = roomRepository
                .findByNumber(roomToUpdate.getNumber());
            if (roomFoundByNumber.getId().equals(roomToUpdate.getId())) {
                roomToReturn = roomRepository.save(roomToUpdate);
            }

            return roomToReturn;
        }
    }
}
