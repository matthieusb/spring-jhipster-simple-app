package web.rest;

import web.rest.util.ResponseEntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.TestSupervisorRepository;

@RestController
@RequestMapping("/api/supervisors")
public class TestSupervisorResource {

    private final TestSupervisorRepository testSupervisorRepository;

    @Autowired
    public TestSupervisorResource(TestSupervisorRepository testSupervisorRepository) {
        this.testSupervisorRepository = testSupervisorRepository;
    }

    @GetMapping(produces = "application/json")
    public @ResponseBody
    ResponseEntity<?> getAllTestSupervisors() {
        return ResponseEntityUtils.
                getResponseEntityForMultipleResponses(testSupervisorRepository.findAll());
    }

    @GetMapping(path = "/id/{id}", produces = "application/json")
    public @ResponseBody
    ResponseEntity<?> getTestSupervisorById(@PathVariable(value = "id") String testSupervisorIdToSearch) {
        return ResponseEntityUtils.
                getResponseEntityForSingleResponse(testSupervisorRepository.findById(testSupervisorIdToSearch));
    }

    @PostMapping(path = "/login", produces = "application/json")
    public @ResponseBody
    ResponseEntity<?> getTestSupervisorByLogin(@RequestParam(value = "login") String testSupervisorLoginToSearch) {
        return ResponseEntityUtils.
                getResponseEntityForSingleResponse(testSupervisorRepository.findByLogin(testSupervisorLoginToSearch));
    }
}
