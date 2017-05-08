package web.rest;

import model.TestSupervisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.TestSupervisorRepository;
import web.rest.util.HeaderUtil;

import java.util.List;

@RestController
@RequestMapping(value = "/api/supervisors", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
public class TestSupervisorResource {

    private final TestSupervisorRepository testSupervisorRepository;

    @Autowired
    public TestSupervisorResource(TestSupervisorRepository testSupervisorRepository) {
        this.testSupervisorRepository = testSupervisorRepository;
    }

    @GetMapping
    public @ResponseBody
    ResponseEntity<List<TestSupervisor>> getAllTestSupervisors() {
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

        if (testSupervisorToCreate != null) {
            testSupervisorToCreate.setId(null);
            if (testSupervisorRepository.findByLogin(testSupervisorToCreate.getLogin()) != null) {
                return ResponseEntity.badRequest().body(null);
            } else {
                TestSupervisor testSupervisorOutput = testSupervisorRepository.save(testSupervisorToCreate);
                return ResponseEntity.ok()
                    .headers(HeaderUtil.getStandardHeaders())
                    .body(testSupervisorOutput);
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping(path = "/update")
    public @ResponseBody
    ResponseEntity<TestSupervisor> updateTestSupervisor(@RequestBody TestSupervisor testSupervisorToUpdate) {

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
