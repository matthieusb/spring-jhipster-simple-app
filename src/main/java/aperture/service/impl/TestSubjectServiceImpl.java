package aperture.service.impl;

import aperture.model.Room;
import aperture.model.TestSubject;
import aperture.model.enums.TypeOperation;
import aperture.repository.RoomRepository;
import aperture.repository.TestSubjectRepository;
import aperture.service.TestSubjectService;
import aperture.web.rest.TestSubjectResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service containing all complex operations for test subjects on database.
 */
@Service
public class TestSubjectServiceImpl implements TestSubjectService {

    private final Logger logger = LoggerFactory.getLogger(TestSubjectResource.class);

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

    @Override
    public boolean triggerUpdateTestSubjectRoomInfo(String idTestSubjectToUpdate) {
        TestSubject testSubjectToUpdate = this.testSubjectRepository.findById(idTestSubjectToUpdate);

        if (testSubjectToUpdate == null) {
            return false;
        } else {
            doUpdateTestSubjectRoomInfo(testSubjectToUpdate);
            return true;
        }
    }

    @Override
    public boolean doUpdateTestSubjectRoomInfoForAllOfThem() {
        logger.info("Launching doUpdateTestSubjectRoomInfoForAllOfThem()");

        List<TestSubject> allTestSubjectsToUpdate = this.testSubjectRepository.findAll();

        if (allTestSubjectsToUpdate != null) {
            allTestSubjectsToUpdate.forEach(
                this::doUpdateTestSubjectRoomInfo
            );
            return true;
        } else {
            logger.error("Error in doUpdateTestSubjectRoomInfoForAllOfThem() : no testsubject to update found");
            return false;
        }
    }

    @Async
    @Override
    public void doUpdateTestSubjectRoomInfo(TestSubject testSubject) {
        logger.info("Launching doUpdateTestSubjectRoomInfo() on : " + testSubject);

        List<Room> listOfRoomsUpdated = new ArrayList<>();

        for (Room currentRoom : testSubject.getRooms()) {
            Room currentRoomStateInDatabase = this.roomRepository.findById(currentRoom.getId());
            if (currentRoomStateInDatabase != null) { // If the room still exists
                if (currentRoomStateInDatabase.equals(currentRoom)) {
                    listOfRoomsUpdated.add(currentRoom);
                } else {
                    listOfRoomsUpdated.add(currentRoomStateInDatabase);
                }
            }
        }

        testSubject.setRooms(listOfRoomsUpdated);
        this.testSubjectRepository.save(testSubject);
        logger.info("Finished doUpdateTestSubjectRoomInfo() on : " + testSubject);
    }
}
