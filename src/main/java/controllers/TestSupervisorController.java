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

    @RequestMapping(path = "/supervisors", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    ResponseEntity<?> getAllTestSupervisors() {
        return ResponseEntityOperations.
                getResponseEntityForMultipleResponses(testSupervisorRepository.findAll());
    }

    @RequestMapping(path = "/supervisors/id/{id}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    ResponseEntity<?> getTestSupervisorById(@PathVariable(value = "id") String testSupervisorIdToSearch) {
        return ResponseEntityOperations.
                getResponseEntityForSingleResponse(testSupervisorRepository.findById(testSupervisorIdToSearch));
    }

    @RequestMapping(path = "/supervisors/login", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    ResponseEntity<?> getTestSupervisorByLogin(@RequestParam(value = "login") String testSupervisorLoginToSearch) {
        return ResponseEntityOperations.
                getResponseEntityForSingleResponse(testSupervisorRepository.findByLogin(testSupervisorLoginToSearch));
    }
}
