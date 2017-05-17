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

    /**
     * Test supervisor unique identifier.
     */
    @Id
    private String id;

    /**
     * Test supervisor unique login.
     */
    @Field("login")
    private String login;

    /**
     * Test supervisor password.
     */
    @Field("pass")
    private String pass;

    /**
     * Default empty constructor.
     */
    public TestSupervisor() {
        this.id = "";
        this.login = "";
        this.pass = "";
    }

    /**
     * Complete constructor with all attributes.
     *
     * @param idToSet    the id to set, not null.
     * @param loginToSet the login to set, not null.
     * @param passToSet  the password to set, not null.
     */
    public TestSupervisor(String idToSet, String loginToSet, String passToSet) {
        this.id = idToSet;
        this.login = loginToSet;
        this.pass = passToSet;
    }

    /**
     * Gets the id.
     *
     * @return the id.
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param idToSet the id to set.
     */
    public void setId(String idToSet) {
        this.id = idToSet;
    }

    /**
     * Gets the login.
     *
     * @return the login.
     */
    public String getLogin() {
        return login;
    }

    /**
     * Sets the login.
     *
     * @param loginToSet the login to set.
     */
    public void setLogin(String loginToSet) {
        this.login = loginToSet;
    }

    /**
     * Gets the password.
     *
     * @return the password.
     */
    public String getPass() {
        return pass;
    }

    /**
     * Sets the password.
     *
     * @param passToSet the password to set.
     */
    public void setPass(String passToSet) {
        this.pass = passToSet;
    }

    /**
     * Displays the test supervisor in console.
     *
     * @return a string with this format : TestSupervisor[id=, number, name].
     */
    @Override
    public String toString() {
        return String.format(
            "TestSupervisor[id=%s, number=%s, name=%s]",
            id, login, pass
        );
    }

    /**
     * Equals method override. Tests all attributes equality.
     *
     * @param objToCompare the object to compare,
     *                     should ideally be a TestSupervisor.
     * @return a boolean value.
     */
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

    /**
     * Hashcode override method for TestSupervisor.
     *
     * @return a unique hashcode as an int.
     */
    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = result + login.hashCode();
        result = result + pass.hashCode();
        return result;
    }
}
