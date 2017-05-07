package web.rest;

import model.TestSubject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.TestSubjectRepository;
import web.rest.util.ResponseEntityUtils;

import java.util.List;

@RestController
@RequestMapping(value = "/api/subjects", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
public class TestSubjectResource {//TODO REFACTO (See RoomResource)

    private final TestSubjectRepository testSubjectRepository;

    @Autowired
    public TestSubjectResource(TestSubjectRepository testSubjectRepository) {
        this.testSubjectRepository = testSubjectRepository;
    }

    @GetMapping(produces = "application/json")
    public @ResponseBody
    ResponseEntity<List<TestSubject>> getAllTestSubjects() {
        List<TestSubject> testSubjects = testSubjectRepository.findAll();
        if (testSubjects.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok()
                .headers(ResponseEntityUtils.getStandardHeaders())
                .body(testSubjects);
        }
    }

    @GetMapping(path = "/id/{id}", produces = "application/json")
    public @ResponseBody
    ResponseEntity<TestSubject> getTestSubjectById(@PathVariable(value = "id") String testSubjectIdToSearch) {
        TestSubject testSubject = testSubjectRepository.findById(testSubjectIdToSearch);
        if (testSubject == null) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok()
                .headers(ResponseEntityUtils.getStandardHeaders())
                .body(testSubject);
        }
    }

    @PostMapping(path = "/name", produces = "application/json")
    public @ResponseBody
    ResponseEntity<?> getTestSubjectByName(@RequestParam(value = "name") String testSubjectNameToSearch) {
        List<TestSubject> testSubjects = testSubjectRepository.findByName(testSubjectNameToSearch);
        if (testSubjects.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok()
                .headers(ResponseEntityUtils.getStandardHeaders())
                .body(testSubjects.get(0));
        }
    }

}
