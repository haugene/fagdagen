package domain.forms;

import models.User;

/**
 * Represents the Login form
 */
public class Login {

    public String username;
    public String password;

    public String validate() {

        // Don't allow blank login
        if(username.isEmpty() || password.isEmpty()) {
            return "Username or password cannot be blank";
        }

        if(User.authenticate(username, password) == null) {
            return "Invalid user or password";
        }

        // Success
        return null;
    }

}