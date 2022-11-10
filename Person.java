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
    
    private String filePath;

    public Person(String username , String password, String email, String filePath) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.filePath = filePath;
    }

    public Person() {
        username = null;
        password = null;
        email = null;
        filePath = null;
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

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        
    }

}
