package repository;

import model.Room;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


@SuppressWarnings("unchecked")
public interface RoomRepository extends MongoRepository<Room, String> {
    Room findById(String id);

    Room findByNumber(Integer number);

    List<Room> findByName(String name);

    @Override
    List<Room> findAll();

    @Override
    Room save(Room room);

    @Override
    void delete(Room room);
}
