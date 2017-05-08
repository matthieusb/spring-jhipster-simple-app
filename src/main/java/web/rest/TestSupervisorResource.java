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
public class TestSupervisorResource {//TODO REFACTO (See RoomResource)

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
    ResponseEntity<TestSupervisor> createTestSupervisor(@RequestBody TestSupervisor testSupervisor) {

        if (testSupervisor != null) {
            testSupervisor.setId(null);
            if (testSupervisorRepository.findByLogin(testSupervisor.getLogin()) != null) {
                return ResponseEntity.badRequest().body(null);
            } else {
                TestSupervisor testSupervisorOutput = testSupervisorRepository.save(testSupervisor);
                return ResponseEntity.ok()
                    .headers(HeaderUtil.getStandardHeaders())
                    .body(testSupervisorOutput);
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
