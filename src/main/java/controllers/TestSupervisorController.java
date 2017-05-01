package controllers;

import controllers.common.ResponseEntityOperations;
import model.TestSupervisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import repositories.TestSupervisorRepository;

import java.util.List;

@RestController
public class TestSupervisorController {

    private final TestSupervisorRepository testSupervisorRepository;

    @Autowired
    public TestSupervisorController(TestSupervisorRepository testSupervisorRepository) {
        this.testSupervisorRepository = testSupervisorRepository;
    }

    @GetMapping(path = "/supervisors", produces = "application/json")
    public @ResponseBody
    ResponseEntity<?> getAllTestSupervisors() {
        return ResponseEntityOperations.
                getResponseEntityForMultipleResponses(testSupervisorRepository.findAll());
    }

    @GetMapping(path = "/supervisors/id/{id}", produces = "application/json")
    public @ResponseBody
    ResponseEntity<?> getTestSupervisorById(@PathVariable(value = "id") String testSupervisorIdToSearch) {
        return ResponseEntityOperations.
                getResponseEntityForSingleResponse(testSupervisorRepository.findById(testSupervisorIdToSearch));
    }

    @PostMapping(path = "/supervisors/login", produces = "application/json")
    public @ResponseBody
    ResponseEntity<?> getTestSupervisorByLogin(@RequestParam(value = "login") String testSupervisorLoginToSearch) {
        return ResponseEntityOperations.
                getResponseEntityForSingleResponse(testSupervisorRepository.findByLogin(testSupervisorLoginToSearch));
    }
}
