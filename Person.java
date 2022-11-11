/**
 * A person class
 * 
 * @authors Tyler, Vijay, Shreya, Rohan
 * @version rev1
 */
public class Person {
    /*
     * Username for a user
     */
    private String username;

    /*
     * Password for a user
     */
    private String password;

    private String email;
    
    public Person(String username , String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public Person() {
        username = null;
        password = null;
        email = null;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        String s = "";
        s = s + String.format("Username: %s, " , username);
        s = s + String.format("Password: %s, " , password);
        s = s + String.format("Email: %s, " , email);
        return s;
    }

}
