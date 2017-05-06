package controllers;

import controllers.utils.ResponseEntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repositories.TestSubjectRepository;

@RestController
@RequestMapping("/api/subjects")
public class TestSubjectController {

    private final TestSubjectRepository testSubjectRepository;

    @Autowired
    public TestSubjectController(TestSubjectRepository testSubjectRepository) {
        this.testSubjectRepository = testSubjectRepository;
    }

    @GetMapping(produces = "application/json")
    public @ResponseBody
    ResponseEntity<?> getAllTestSubjects() {
        return ResponseEntityUtils.
                getResponseEntityForMultipleResponses(testSubjectRepository.findAll());
    }

    @GetMapping(path = "/id/{id}", produces = "application/json")
    public @ResponseBody
    ResponseEntity<?> getTestSubjectById(@PathVariable(value = "id") String testSubjectIdToSearch) {
        return ResponseEntityUtils.
                getResponseEntityForSingleResponse(testSubjectRepository.findById(testSubjectIdToSearch));
    }

    @PostMapping(path = "/name", produces = "application/json")
    public @ResponseBody
    ResponseEntity<?> getTestSubjectByName(@RequestParam(value = "name") String testSubjectNameToSearch) {
        return ResponseEntityUtils.
                getResponseEntityForMultipleResponses(testSubjectRepository.findByName(testSubjectNameToSearch));
    }

}
