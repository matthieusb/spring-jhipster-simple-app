package aperture.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Test supervisor model class.
 * The TestSupervisor is here to make jokes
 * and cruelly assign test subjects to their rooms (See Glados for example).
 */
@Document(collection = "TestSupervisors")
public class TestSupervisor {

    @Id
    private String id;

    @Field("login")
    private String login;

    @Field("pass")
    private String pass;

    public TestSupervisor() {
        this.id = "";
        this.login = "";
        this.pass = "";
    }

    public TestSupervisor(String idToSet, String loginToSet, String passToSet) {
        this.id = idToSet;
        this.login = loginToSet;
        this.pass = passToSet;
    }

    public String getId() {
        return id;
    }

    public void setId(String idToSet) {
        this.id = idToSet;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String loginToSet) {
        this.login = loginToSet;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String passToSet) {
        this.pass = passToSet;
    }

    @Override
    public String toString() {
        return String.format(
            "TestSupervisor[id=%s, number=%s, name=%s]",
            id, login, pass
        );
    }

    @Override
    public boolean equals(Object objToCompare) {
        if (objToCompare == this) {
            return true;
        }

        if (objToCompare instanceof TestSupervisor) {
            TestSupervisor objAsTestSupervisor = (TestSupervisor) objToCompare;
            return objAsTestSupervisor.getId().equals(this.getId())
                && objAsTestSupervisor.getLogin().equals(this.getLogin())
                && objAsTestSupervisor.getPass().equals(this.getPass());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = result + login.hashCode();
        result = result + pass.hashCode();
        return result;
    }
}
