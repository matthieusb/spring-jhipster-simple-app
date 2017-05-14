package aperture.service;


import aperture.model.Room;
import aperture.model.enums.TypeOperation;

public interface RoomService {
    Room createOrUpdateRoom(Room roomToCreateOrUpdate, TypeOperation typeOperation);

    Room createTestRoom(Room roomToCreate);

    Room updateTestRoom(Room roomToUpdate);

}
