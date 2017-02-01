package repositories;

import model.Room;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface RoomRepository extends MongoRepository<Room, String> {
    Room findById(String id);
    List<Room> findByNumber(Integer number);
    List<Room> findAll();
}
