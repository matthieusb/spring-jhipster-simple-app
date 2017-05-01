package controllers;

import controllers.common.ResponseEntityOperations;
import model.TestSubject;
import org.omg.CORBA.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import repositories.TestSubjectRepository;

import java.util.List;

@RestController
public class TestSubjectController {

    private final TestSubjectRepository testSubjectRepository;

    @Autowired
    public TestSubjectController(TestSubjectRepository testSubjectRepository) {
        this.testSubjectRepository = testSubjectRepository;
    }

    @GetMapping(path = "/subjects", produces = "application/json")
    public @ResponseBody
    ResponseEntity<?> getAllTestSubjects() {
        return ResponseEntityOperations.
                getResponseEntityForMultipleResponses(testSubjectRepository.findAll());
    }

    @GetMapping(path = "/subjects/id/{id}", produces = "application/json")
    public @ResponseBody
    ResponseEntity<?> getTestSubjectById(@PathVariable(value = "id") String testSubjectIdToSearch) {
        return ResponseEntityOperations.
                getResponseEntityForSingleResponse(testSubjectRepository.findById(testSubjectIdToSearch));
    }

    @PostMapping(path = "/subjects/name", produces = "application/json")
    public @ResponseBody
    ResponseEntity<?> getTestSubjectByName(@RequestParam(value = "name") String testSubjectNameToSearch) {
        return ResponseEntityOperations.
                getResponseEntityForMultipleResponses(testSubjectRepository.findByName(testSubjectNameToSearch));
    }

}
