package aperture.service;


import aperture.model.TestSubject;
import aperture.model.enums.TypeOperation;

public interface TestSubjectService {

    TestSubject createOrUpdateTestSubject(TestSubject testSubjectToCreateOrUpdate, TypeOperation typeOperation);

    TestSubject createTestSubject(TestSubject testSubjectToCreate);

    boolean testSubjectIsValidForInsert(TestSubject testSubjectToTest);

    TestSubject updateTestSubject(TestSubject testSubjectToUpdate);

    boolean testSubjectIsValidForUpdate(TestSubject testSubjectToTest);

    boolean allRoomsProvidedExist(TestSubject testSubject);

    /**
     * Launches room info update for a particular test subject.
     *
     * @param idTestSubjectToUpdate the identifier of the test subject to update.
     * @return an error if the test subject does not exist.
     */
    boolean triggerUpdateTestSubjectRoomInfo(String idTestSubjectToUpdate);

    /**
     * Effectively does the room info update for all test subjects.
     */
    boolean doUpdateTestSubjectRoomInfoForAllOfThem();

    /**
     * Effectively does the room info update for a test subject.
     * This method updates the room elements of a test subject with the latest data available in the Room table.
     *
     * @param testSubject the test subject to update.
     */
    void doUpdateTestSubjectRoomInfo(TestSubject testSubject);
}
