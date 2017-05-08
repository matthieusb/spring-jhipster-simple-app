package web.rest;

import model.TestSubject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.RoomRepository;
import repository.TestSubjectRepository;
import web.rest.util.HeaderUtil;

import java.util.List;

@RestController
@RequestMapping(value = "/api/subjects", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
public class TestSubjectResource {//TODO REFACTO (See RoomResource)

    private final RoomRepository roomRepository;

    private final TestSubjectRepository testSubjectRepository;

    @Autowired
    public TestSubjectResource(TestSubjectRepository testSubjectRepository, RoomRepository roomRepository) {
        this.testSubjectRepository = testSubjectRepository;
        this.roomRepository = roomRepository;
    }

    @GetMapping(produces = "application/json")
    public @ResponseBody
    ResponseEntity<List<TestSubject>> getAllTestSubjects() {
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
        if (testSubjectToCreate != null) {
            testSubjectToCreate.setId(null);
            if (testSubjectRepository.findByName(testSubjectToCreate.getName()) != null) {
                return ResponseEntity.badRequest().body(null);
            } else {
                boolean allRoomsProvidedExist = testSubjectToCreate.getRooms().stream()
                    .allMatch(subjectRoom -> roomRepository.findById(subjectRoom.getId()) != null);
                if (allRoomsProvidedExist) {
                    TestSubject testSubjectOutput = testSubjectRepository.save(testSubjectToCreate);
                    return ResponseEntity.ok()
                        .headers(HeaderUtil.getStandardHeaders())
                        .body(testSubjectOutput);
                } else {
                    return ResponseEntity.badRequest().body(null);
                }
            }
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping(path = "/delete/{id}")
    public @ResponseBody
    ResponseEntity<TestSubject> deleteTestSubject(@PathVariable(value = "id") String idTestSubjectToDelete) {
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
