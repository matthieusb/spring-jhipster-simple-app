package model;


public class TestSupervisor {
    private Long testSupervisorId;
    private String login;
    private String pass;

    public TestSupervisor(Long testSupervisorId, String login, String pass) {
        this.testSupervisorId = testSupervisorId;
        this.login = login;
        this.pass = pass;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public Long getTestSupervisorId() {
        return testSupervisorId;
    }

    public void setTestSupervisorId(Long testSupervisorId) {
        this.testSupervisorId = testSupervisorId;
    }
}
