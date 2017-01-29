package repositories;

import model.TestSubject;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface TestSubjectRepository extends MongoRepository<TestSubject, String> {
    TestSubject findById(String id);
}
