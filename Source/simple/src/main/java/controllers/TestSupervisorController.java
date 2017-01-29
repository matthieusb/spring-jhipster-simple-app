package controllers;

import model.TestSupervisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import repositories.TestSupervisorRepository;

import java.util.List;

@Controller
public class TestSupervisorController {

    private final TestSupervisorRepository testSupervisorRepository;

    @Autowired
    public TestSupervisorController(TestSupervisorRepository testSupervisorRepository) {
        this.testSupervisorRepository = testSupervisorRepository;
    }

    @RequestMapping(path = "/supervisors", method = RequestMethod.GET)
    public @ResponseBody
    List<TestSupervisor> getAllTestSupervisors() {
        return testSupervisorRepository.findAll();
    }
}
