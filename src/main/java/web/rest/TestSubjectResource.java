package web.rest;

import web.rest.util.ResponseEntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.TestSubjectRepository;

@RestController
@RequestMapping("/api/subjects")
public class TestSubjectResource {

    private final TestSubjectRepository testSubjectRepository;

    @Autowired
    public TestSubjectResource(TestSubjectRepository testSubjectRepository) {
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
