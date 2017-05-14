package aperture.service.impl;


import aperture.model.TestSupervisor;
import aperture.model.enums.TypeOperation;
import aperture.repository.TestSupervisorRepository;
import aperture.service.TestSupervisorService;
import org.springframework.stereotype.Service;

@Service
public class TestSupervisorServiceImpl implements TestSupervisorService {

    private TestSupervisorRepository testSupervisorRepository;

    public TestSupervisorServiceImpl(TestSupervisorRepository testSupervisorRepository) {
        this.testSupervisorRepository = testSupervisorRepository;
    }

    @Override
    public TestSupervisor createOrUpdateTestSupervisor(TestSupervisor testSupervisorToCreateOrUpdate, TypeOperation typeOperation) {
        if (testSupervisorToCreateOrUpdate == null) {
            return null;
        } else {
            TestSupervisor testSupervisorToReturn = null;
            if (typeOperation == TypeOperation.CREATE) {
                testSupervisorToReturn = createTestSupervisor(testSupervisorToCreateOrUpdate);
            } else if (typeOperation == TypeOperation.UPDATE) {
                testSupervisorToReturn = updateTestSupervisor(testSupervisorToCreateOrUpdate);
            }
            return testSupervisorToReturn;
        }
    }

    @Override
    public TestSupervisor createTestSupervisor(TestSupervisor testSupervisorToCreate) {

        testSupervisorToCreate.setId(null);
        if (testSupervisorRepository.findByLogin(testSupervisorToCreate.getLogin()) != null) {
            return null;
        } else {
            return testSupervisorRepository.save(testSupervisorToCreate);
        }
    }

    @Override
    public TestSupervisor updateTestSupervisor(TestSupervisor testSupervisorToUpdate) {
        if (testSupervisorRepository.findById(testSupervisorToUpdate.getId()) == null) {
            return null;
        } else {
            TestSupervisor testSupervisorFoundByLogin = testSupervisorRepository.findByLogin(testSupervisorToUpdate.getLogin());
            
            TestSupervisor testSupervisorOutput = null;
            if (testSupervisorFoundByLogin == null || testSupervisorFoundByLogin.getId().equals(testSupervisorToUpdate.getId())) {
                testSupervisorOutput = testSupervisorRepository.save(testSupervisorToUpdate);
            }

            return testSupervisorOutput;
        }
    }
}
