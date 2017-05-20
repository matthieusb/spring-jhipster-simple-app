package aperture.model;


public class AccountCredentials {
    private String username;
    private String password;

    public AccountCredentials() {
        username = "";
        password = "";
    }

    public AccountCredentials(String usernameToSet, String passwordToSet) {
        this.username = usernameToSet;
        this.password = passwordToSet;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String usernameToSet) {
        this.username = usernameToSet;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String passwordToSet) {
        this.password = passwordToSet;
    }
}
