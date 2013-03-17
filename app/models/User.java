package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

import com.avaje.ebean.*;

/**
 * User entity managed by Ebean
 */
@Entity
@Table(name="account")
public class User extends Model {

    @Id
    public Long id;

    @Constraints.Required
    public String username;

    @Constraints.Required
    public String password;

    // -- Queries

    public static Model.Finder<String,User> find = new Model.Finder(String.class, User.class);

    /**
     * Retrieve all users.
     */
    public static List<User> findAll() {
        return find.all();
    }

    /**
     * Retrieve a User from email.
     */
    public static User findByUsername(String username)
    {
        return find.where().eq("username", username).findUnique();
    }

    /**
     * Authenticate a User.
     */
    public static User authenticate(String username, String password)
    {
        // Simply add the first user to login. We only have one admin, simple!
        addUserIfNoUsersExist(username, password);

        return find.where()
                .eq("username", username)
                .eq("password", password)
                .findUnique();
    }

    public String toString() {
        return "User(" + username + ")";
    }

    /**
     * We decided that we didn't want a signup page and decided to do the user administration simple.
     * The first time a user logs in, that user is created. We only need one user(ie admin),
     * and from that point all logins are verified towards this user.
     *
     * @param username
     * @param password
     */
    private static void addUserIfNoUsersExist(String username, String password)
    {
        // If no user exist, add this one
        if(findAll().isEmpty()){
            User user = new User();
            user.username = username;
            user.password = password;
            user.save();
        }
    }
}

