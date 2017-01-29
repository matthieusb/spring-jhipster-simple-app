package model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "TestSupervisors")
public class TestSupervisor {

    @Id
    private String id;

    @Field("login")
    private String login;

    @Field("pass")
    private String pass;

    public TestSupervisor(String id, String login, String pass) {
        this.id = id;
        this.login = login;
        this.pass = pass;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

}
