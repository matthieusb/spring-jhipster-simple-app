package aperture.web.rest;

import aperture.model.TestSupervisor;
import aperture.model.enums.TypeOperation;
import aperture.repository.TestSupervisorRepository;
import aperture.service.TestSupervisorService;
import aperture.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Resource containing all web rest operations for TestSupervisors.
 */
@RestController
@RequestMapping(value = "/api/supervisors", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
public class TestSupervisorResource {

    private final Logger logger = LoggerFactory.getLogger(TestSupervisorResource.class);

    private final TestSupervisorService testSupervisorService;

    private final TestSupervisorRepository testSupervisorRepository;

    public TestSupervisorResource(TestSupervisorService testSupervisorServiceToSet,
                                  TestSupervisorRepository testSupervisorRepositoryToSet) {
        this.testSupervisorService = testSupervisorServiceToSet;
        this.testSupervisorRepository = testSupervisorRepositoryToSet;
    }

    @GetMapping
    public @ResponseBody
    ResponseEntity<List<TestSupervisor>> getAllTestSupervisors() {
        logger.info("REST request to getAllTestSupervisors()");

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
        logger.info("REST request to getTestSupervisorById() : " + testSupervisorIdToSearch);

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
    ResponseEntity<TestSupervisor> getTestSupervisorByLogin(
        @RequestParam(value = "login") String testSupervisorLoginToSearch
    ) {
        logger.info("REST request to getTestSupervisorByLogin() : " + testSupervisorLoginToSearch);

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
        logger.info("REST request to createTestSupervisor() : " + testSupervisorToCreate);

        TestSupervisor testSupervisorOutput = testSupervisorService
            .createOrUpdateTestSupervisor(testSupervisorToCreate, TypeOperation.CREATE);

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
        logger.info("REST request to updateTestSupervisor() : " + testSupervisorToUpdate);
        TestSupervisor testSupervisorOutput = testSupervisorService
            .createOrUpdateTestSupervisor(testSupervisorToUpdate, TypeOperation.UPDATE);

        if (testSupervisorOutput != null) {
            return ResponseEntity.ok()
                .headers(HeaderUtil.getStandardHeaders())
                .body(testSupervisorOutput);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping(path = "/delete/{id}")
    public @ResponseBody
    ResponseEntity<TestSupervisor> deleteTestSupervisor(@PathVariable(value = "id") String idTestSupervisorToDelete) {
        logger.info("REST request to deleteTestSupervisor() : " + idTestSupervisorToDelete);

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
