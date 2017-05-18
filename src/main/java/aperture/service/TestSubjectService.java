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

    boolean triggerUpdateTestSubjectRoomInfo(String idTestSubjectToUpdate);


    void doUpdateTestSubjectRoomInfoForAllOfThem();

    void doUpdateTestSubjectRoomInfo(TestSubject testSubject);
}
