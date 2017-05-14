package aperture.service.impl;

import aperture.model.TestSubject;
import aperture.model.enums.TypeOperation;
import aperture.repository.RoomRepository;
import aperture.repository.TestSubjectRepository;
import aperture.service.TestSubjectService;
import org.springframework.stereotype.Service;

@Service
public class TestSubjectServiceImpl implements TestSubjectService {

    private final RoomRepository roomRepository;

    private final TestSubjectRepository testSubjectRepository;

    public TestSubjectServiceImpl(RoomRepository roomRepository, TestSubjectRepository testSubjectRepository) {
        this.roomRepository = roomRepository;
        this.testSubjectRepository = testSubjectRepository;
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
        if (testSubjectRepository.findByName(testSubjectToCreate.getName()) != null || !allRoomsProvidedExist(testSubjectToCreate)) {
            return null;
        } else {
            return testSubjectRepository.save(testSubjectToCreate);
        }
    }

    @Override
    public TestSubject updateTestSubject(TestSubject testSubjectToUpdate) {
        if (testSubjectRepository.findById(testSubjectToUpdate.getId()) == null || !allRoomsProvidedExist(testSubjectToUpdate)) {
            return null;
        } else {
            TestSubject testSubjectFoundByName = testSubjectRepository.findByName(testSubjectToUpdate.getName());
            if (testSubjectFoundByName == null || testSubjectFoundByName.getId().equals(testSubjectToUpdate.getId())) {
                return testSubjectRepository.save(testSubjectToUpdate);
            } else {
                return null;
            }
        }
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
