package repositories;

import model.TestSupervisor;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface TestSupervisorRepository extends MongoRepository<TestSupervisor, String> {
    TestSupervisor findById(String id);
}
