package controllers;

import model.TestSubject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import repositories.TestSubjectRepository;

import java.util.List;

@RestController
public class TestSubjectController {

    private final TestSubjectRepository testSubjectRepository;

    @Autowired
    public TestSubjectController(TestSubjectRepository testSubjectRepository) {
        this.testSubjectRepository = testSubjectRepository;
    }

    @RequestMapping(path = "/subjects", method = RequestMethod.GET)
    public @ResponseBody
    List<TestSubject> getAllTestSubjects() {
        return testSubjectRepository.findAll();
    }

}
