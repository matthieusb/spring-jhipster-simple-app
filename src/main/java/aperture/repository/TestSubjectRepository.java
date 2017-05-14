package aperture.repository;

import aperture.model.TestSubject;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


@SuppressWarnings("unchecked")
public interface TestSubjectRepository extends MongoRepository<TestSubject, String> {
    TestSubject findById(String id);

    TestSubject findByName(String name);

    @Override
    List<TestSubject> findAll();

    @Override
    TestSubject save(TestSubject testSubject);

    @Override
    void delete(TestSubject testSubject);
}
