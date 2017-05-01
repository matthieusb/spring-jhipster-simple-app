package repositories;

import model.TestSupervisor;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface TestSupervisorRepository extends MongoRepository<TestSupervisor, String> {
    TestSupervisor findById(String id);
    TestSupervisor findByLogin(String login);
    List<TestSupervisor> findAll();
}
