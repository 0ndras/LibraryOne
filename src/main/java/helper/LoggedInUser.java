package helper;

public class LoggedInUser {
    private static LoggedInUser instance;
    private String username;

    private LoggedInUser() {}

    public static LoggedInUser getInstance() {
        if (instance == null) {
            instance = new LoggedInUser();
        }
        return instance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
