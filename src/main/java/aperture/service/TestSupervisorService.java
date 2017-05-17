package aperture.service;


import aperture.model.TestSupervisor;
import aperture.model.enums.TypeOperation;

public interface TestSupervisorService {
    TestSupervisor createOrUpdateTestSupervisor(TestSupervisor testSupervisorToCreateOrUpdate,
                                                TypeOperation typeOperation);

    TestSupervisor createTestSupervisor(TestSupervisor testSupervisorToCreate);

    TestSupervisor updateTestSupervisor(TestSupervisor testSupervisorToUpdate);

    boolean testSupervisorIsValidForUpdate(TestSupervisor testSupervisorFoundByLogin,
                                           TestSupervisor testSupervisorToTest);
}
