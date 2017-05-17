package aperture.repository;

import aperture.model.TestSupervisor;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


/**
 * MongoRepository containing all database operations for TestSupervisors.
 */
@SuppressWarnings("unchecked")
public interface TestSupervisorRepository extends MongoRepository<TestSupervisor, String> {
    TestSupervisor findById(String id);

    TestSupervisor findByLogin(String login);

    @Override
    List<TestSupervisor> findAll();

    @Override
    TestSupervisor save(TestSupervisor testSupervisor);

    @Override
    void delete(TestSupervisor testSupervisor);
}
