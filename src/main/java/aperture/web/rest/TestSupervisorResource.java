package aperture.web.rest;

import aperture.model.TestSupervisor;
import aperture.model.enums.TypeOperation;
import aperture.repository.TestSupervisorRepository;
import aperture.service.TestSupervisorService;
import aperture.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/supervisors", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
public class TestSupervisorResource {

    private final Logger LOGGER = LoggerFactory.getLogger(TestSupervisorResource.class);

    private final TestSupervisorService testSupervisorService;

    private final TestSupervisorRepository testSupervisorRepository;

    @Autowired
    public TestSupervisorResource(TestSupervisorService testSupervisorService, TestSupervisorRepository testSupervisorRepository) {
        this.testSupervisorService = testSupervisorService;
        this.testSupervisorRepository = testSupervisorRepository;
    }

    @GetMapping
    public @ResponseBody
    ResponseEntity<List<TestSupervisor>> getAllTestSupervisors() {
        LOGGER.info("REST request to getAllTestSupervisors()");

        List<TestSupervisor> testSupervisors = testSupervisorRepository.findAll();
        if (testSupervisors.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok()
                .headers(HeaderUtil.getStandardHeaders())
                .body(testSupervisors);
        }
    }

    @GetMapping(path = "/id/{id}")
    public @ResponseBody
    ResponseEntity<TestSupervisor> getTestSupervisorById(@PathVariable(value = "id") String testSupervisorIdToSearch) {
        LOGGER.info("REST request to getTestSupervisorById() : " + testSupervisorIdToSearch);

        TestSupervisor testSupervisor = testSupervisorRepository.findById(testSupervisorIdToSearch);
        if (testSupervisor == null) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok()
                .headers(HeaderUtil.getStandardHeaders())
                .body(testSupervisor);
        }

    }

    @PostMapping(path = "/login")
    public @ResponseBody
    ResponseEntity<TestSupervisor> getTestSupervisorByLogin(@RequestParam(value = "login") String testSupervisorLoginToSearch) {
        LOGGER.info("REST request to getTestSupervisorByLogin() : " + testSupervisorLoginToSearch);

        TestSupervisor testSupervisor = testSupervisorRepository.findByLogin(testSupervisorLoginToSearch);
        if (testSupervisor == null) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok()
                .headers(HeaderUtil.getStandardHeaders())
                .body(testSupervisor);
        }
    }

    @PostMapping(path = "/create")
    public @ResponseBody
    ResponseEntity<TestSupervisor> createTestSupervisor(@RequestBody TestSupervisor testSupervisorToCreate) {
        LOGGER.info("REST request to createTestSupervisor() : " + testSupervisorToCreate);

        TestSupervisor testSupervisorOutput = testSupervisorService.createOrUpdateTestSupervisor(testSupervisorToCreate, TypeOperation.CREATE);

        if (testSupervisorOutput != null) {
            return ResponseEntity.ok()
                .headers(HeaderUtil.getStandardHeaders())
                .body(testSupervisorOutput);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping(path = "/update")
    public @ResponseBody
    ResponseEntity<TestSupervisor> updateTestSupervisor(@RequestBody TestSupervisor testSupervisorToUpdate) {
        LOGGER.info("REST request to updateTestSupervisor() : " + testSupervisorToUpdate);

        if (testSupervisorToUpdate != null) {
            if (testSupervisorRepository.findById(testSupervisorToUpdate.getId()) == null) {
                return ResponseEntity.badRequest().body(null);
            } else {
                TestSupervisor testSupervisorFoundByLogin = testSupervisorRepository.findByLogin(testSupervisorToUpdate.getLogin());

                if (testSupervisorFoundByLogin == null || testSupervisorFoundByLogin.getId().equals(testSupervisorToUpdate.getId())) {
                    TestSupervisor testSupervisorOutput = testSupervisorRepository.save(testSupervisorToUpdate);
                    return ResponseEntity.ok()
                        .headers(HeaderUtil.getStandardHeaders())
                        .body(testSupervisorOutput);
                } else {
                    return ResponseEntity.badRequest().build();
                }
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping(path = "/delete/{id}")
    public @ResponseBody
    ResponseEntity<TestSupervisor> deleteTestSupervisor(@PathVariable(value = "id") String idTestSupervisorToDelete) {
        LOGGER.info("REST request to deleteTestSupervisor() : " + idTestSupervisorToDelete);

        TestSupervisor testSupervisorToDelete = testSupervisorRepository.findById(idTestSupervisorToDelete);

        if (testSupervisorToDelete == null) {
            return ResponseEntity.badRequest().body(null);
        } else {
            testSupervisorRepository.delete(testSupervisorToDelete);
            return ResponseEntity.ok()
                .headers(HeaderUtil.getStandardHeaders())
                .body(testSupervisorToDelete);
        }
    }
}
