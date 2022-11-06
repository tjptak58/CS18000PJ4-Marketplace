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

    public Person(String username , String password) {
        this.username = username;
        this.password = password;
    }

    public Person() {
        username = null;
        password = null;
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

}