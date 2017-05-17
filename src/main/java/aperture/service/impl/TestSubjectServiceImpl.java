package aperture.service.impl;

import aperture.model.TestSubject;
import aperture.model.enums.TypeOperation;
import aperture.repository.RoomRepository;
import aperture.repository.TestSubjectRepository;
import aperture.service.TestSubjectService;
import org.springframework.stereotype.Service;

/**
 * Service containing all complex operations for test subjects on database.
 */
@Service
public class TestSubjectServiceImpl implements TestSubjectService {

    private final RoomRepository roomRepository;

    private final TestSubjectRepository testSubjectRepository;

    public TestSubjectServiceImpl(
        RoomRepository roomRepositoryToSet,
        TestSubjectRepository testSubjectRepositoryToSet) {
        this.roomRepository = roomRepositoryToSet;
        this.testSubjectRepository = testSubjectRepositoryToSet;
    }


    @Override
    public TestSubject createOrUpdateTestSubject(TestSubject testSubjectToCreateOrUpdate, TypeOperation typeOperation) {
        if (testSubjectToCreateOrUpdate == null) {
            return null;
        } else {
            TestSubject testSubjectToReturn = null;
            if (typeOperation == TypeOperation.CREATE) {
                testSubjectToReturn = createTestSubject(testSubjectToCreateOrUpdate);
            } else if (typeOperation == TypeOperation.UPDATE) {
                testSubjectToReturn = updateTestSubject(testSubjectToCreateOrUpdate);
            }
            return testSubjectToReturn;
        }
    }

    @Override
    public TestSubject createTestSubject(TestSubject testSubjectToCreate) {
        testSubjectToCreate.setId(null);
        if (this.testSubjectIsValidForInsert(testSubjectToCreate)) {
            return null;
        } else {
            return testSubjectRepository.save(testSubjectToCreate);
        }
    }

    @Override
    public boolean testSubjectIsValidForInsert(TestSubject testSubjectToTest) {
        return testSubjectRepository
            .findByName(testSubjectToTest.getName()) != null || !allRoomsProvidedExist(testSubjectToTest);
    }

    @Override
    public TestSubject updateTestSubject(TestSubject testSubjectToUpdate) {
        if (this.testSubjectIsValidForUpdate(testSubjectToUpdate)) {
            return null;
        } else {
            TestSubject testSubjectFoundByName = testSubjectRepository
                .findByName(testSubjectToUpdate.getName());
            if (testSubjectFoundByName == null || testSubjectFoundByName.getId().equals(testSubjectToUpdate.getId())) {
                return testSubjectRepository.save(testSubjectToUpdate);
            } else {
                return null;
            }
        }
    }

    @Override
    public boolean testSubjectIsValidForUpdate(TestSubject testSubjectToTest) {
        return testSubjectRepository
            .findById(testSubjectToTest.getId()) == null || !allRoomsProvidedExist(testSubjectToTest);
    }

    @Override
    public boolean allRoomsProvidedExist(TestSubject testSubject) {
        return testSubject.getRooms().stream()
            .allMatch(subjectRoom ->
                roomRepository.findById(subjectRoom.getId()) != null
                    && !roomRepository.findByName(subjectRoom.getName()).isEmpty()
                    && roomRepository.findByNumber(subjectRoom.getNumber()) != null
            );
    }

}
