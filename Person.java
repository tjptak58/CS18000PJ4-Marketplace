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
    
    private String filePath;

    public Person(String username , String password, String filePath) {
        this.username = username;
        this.password = password;
        this.filePath = filePath;
    }

    public Person() {
        username = null;
        password = null;
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

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

}
