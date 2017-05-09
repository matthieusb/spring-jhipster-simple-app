package aperture.service;


import aperture.model.TestSubject;
import aperture.model.enums.TypeOperation;

public interface TestSubjectService {

    TestSubject createOrUpdateTestSubject(TestSubject testSubjectToCreateOrupdate, TypeOperation typeOperation);

    TestSubject createTestSubject(TestSubject testSubjectToCreate);

    boolean allRoomsProvidedExist(TestSubject testSubject);
}
