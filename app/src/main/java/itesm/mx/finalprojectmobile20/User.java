package itesm.mx.finalprojectmobile20;

/**
 * Created by Marco on 4/25/15.
 */
public class User {
    private String user;
    private String email;

    private User() {
    }

    User(String user, String email) {
        this.user = user;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getUser() {
        return user;
    }
}
