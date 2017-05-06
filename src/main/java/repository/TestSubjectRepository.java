package repository;

import model.TestSubject;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface TestSubjectRepository extends MongoRepository<TestSubject, String> {
    TestSubject findById(String id);
    List<TestSubject> findByName(String name);
    List<TestSubject> findAll();
}
