package repositories;

import model.Room;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface RoomRepository extends MongoRepository<Room, String> {
    Room findById(String id);
}
