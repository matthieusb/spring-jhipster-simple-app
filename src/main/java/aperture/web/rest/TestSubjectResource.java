package aperture.web.rest;

import aperture.model.TestSubject;
import aperture.model.enums.TypeOperation;
import aperture.repository.TestSubjectRepository;
import aperture.service.TestSubjectService;
import aperture.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/subjects", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
public class TestSubjectResource {

    private final Logger logger = LoggerFactory.getLogger(TestSubjectResource.class);

    private final TestSubjectRepository testSubjectRepository;

    private final TestSubjectService testSubjectService;

    @Autowired
    public TestSubjectResource(TestSubjectRepository testSubjectRepositoryToSet,
                               TestSubjectService testSubjectServiceToSet) {
        this.testSubjectRepository = testSubjectRepositoryToSet;
        this.testSubjectService = testSubjectServiceToSet;
    }

    @GetMapping(produces = "application/json")
    public @ResponseBody
    ResponseEntity<List<TestSubject>> getAllTestSubjects() {
        logger.info("REST request to getAllTestSubjects()");

        List<TestSubject> testSubjects = testSubjectRepository.findAll();
        if (testSubjects.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok()
                .headers(HeaderUtil.getStandardHeaders())
                .body(testSubjects);
        }
    }

    @GetMapping(path = "/id/{id}", produces = "application/json")
    public @ResponseBody
    ResponseEntity<TestSubject> getTestSubjectById(@PathVariable(value = "id") String testSubjectIdToSearch) {
        logger.info("REST request to getTestSubjectById() : " + testSubjectIdToSearch);

        TestSubject testSubject = testSubjectRepository.findById(testSubjectIdToSearch);
        if (testSubject == null) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok()
                .headers(HeaderUtil.getStandardHeaders())
                .body(testSubject);
        }
    }

    @PostMapping(path = "/name", produces = "application/json")
    public @ResponseBody
    ResponseEntity<TestSubject> getTestSubjectByName(@RequestParam(value = "name") String testSubjectNameToSearch) {
        logger.info("REST request to getTestSubjectByName() : " + testSubjectNameToSearch);

        TestSubject testSubject = testSubjectRepository.findByName(testSubjectNameToSearch);
        if (testSubject == null) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok()
                .headers(HeaderUtil.getStandardHeaders())
                .body(testSubject);
        }
    }

    @PostMapping(path = "/create")
    public @ResponseBody
    ResponseEntity<TestSubject> createTestSubject(@RequestBody TestSubject testSubjectToCreate) {
        logger.info("REST request to createTestSupervisor() : " + testSubjectToCreate);
        TestSubject testSubjectOutput = testSubjectService
            .createOrUpdateTestSubject(testSubjectToCreate, TypeOperation.CREATE);

        if (testSubjectOutput != null) {
            return ResponseEntity.ok()
                .headers(HeaderUtil.getStandardHeaders())
                .body(testSubjectOutput);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping(path = "/update")
    public @ResponseBody
    ResponseEntity<TestSubject> updateTestSubject(@RequestBody TestSubject testSubjectToUpdate) {
        logger.info("REST request to updateTestSupervisor() : " + testSubjectToUpdate);

        TestSubject testSubjectOutput = testSubjectService
            .createOrUpdateTestSubject(testSubjectToUpdate, TypeOperation.UPDATE);

        if (testSubjectOutput != null) {
            return ResponseEntity.ok()
                .headers(HeaderUtil.getStandardHeaders())
                .body(testSubjectOutput);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping(path = "/delete/{id}")
    public @ResponseBody
    ResponseEntity<TestSubject> deleteTestSubject(@PathVariable(value = "id") String idTestSubjectToDelete) {
        logger.info("REST request to deleteTestSubject() : " + idTestSubjectToDelete);

        TestSubject testSubjectToDelete = testSubjectRepository.findById(idTestSubjectToDelete);

        if (testSubjectToDelete == null) {
            return ResponseEntity.badRequest().body(null);
        } else {
            testSubjectRepository.delete(testSubjectToDelete);
            return ResponseEntity.ok()
                .headers(HeaderUtil.getStandardHeaders())
                .body(testSubjectToDelete);
        }
    }

}
