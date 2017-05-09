package aperture.service;


import aperture.model.TestSupervisor;
import aperture.model.enums.TypeOperation;

public interface TestSupervisorService {
    TestSupervisor createOrUpdateTestSubject(TestSupervisor testSupervisorToCreateOrUpdate, TypeOperation typeOperation);

    TestSupervisor createTestSubject(TestSupervisor testSupervisorToCreate);

    TestSupervisor updateTestSubject(TestSupervisor testSupervisorToUpdate);
}
