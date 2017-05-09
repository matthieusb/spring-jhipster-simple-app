package aperture.service;


import aperture.model.Room;
import aperture.model.enums.TypeOperation;

public interface RoomService {
    Room createOrUpdateTestSubject(Room roomToCreateOrUpdate, TypeOperation typeOperation);

    Room createTestSubject(Room roomToCreate);

    Room updateTestSubject(Room roomToUpdate);

}
