package commands;

import data.User;
import helper.MyJDBC;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LogInCommandTest {
    private Map<String, User> users;
    private Set<User> loggedInUsers;
    private User loggedInUser;

    @BeforeEach
    void setUp() {
        users = new ConcurrentHashMap<>();
        loggedInUsers = ConcurrentHashMap.newKeySet();
        loggedInUser = null;
    }

    @Test
    void testAlreadyLoggedIn() {
        User user = new User("Nikola", "123");
        loggedInUser = user;
        loggedInUsers.add(user);

        Command login = new LogInCommand("Nikola", "123", loggedInUser, users, loggedInUsers);

        String response = login.execute();

        assertEquals("You are already logged in!", response,
                "Valid command should return \"Yuo are already logged in!\" when you are logged in!");
        assertTrue(loggedInUsers.contains(user),
                "Shouldn't remove user from the data structure!");
    }

    @Test
    void testLoginNonExistentUser() {
        User user = new User("Nikola", "123");

        Command login = new LogInCommand("Nikola", "123", loggedInUser, users, loggedInUsers);

        String response = login.execute();

        assertEquals("There isn't user with this name!", response,
                "Valid command should return \"There isn't user with this name!\" when username is not in registered usernames!");

    }

    @Test
    void testLoginWithWrongPassword() {
        User user = new User("Nikola", "123");
        users.put("Nikola", user);

        Command login = new LogInCommand("Nikola", "Not123", loggedInUser, users, loggedInUsers);

        String response = login.execute();

        assertEquals("Wrong password!", response,
                "Valid command should return \"Wrong password!\" when username is in registered usernames but password is different!");

    }

    @Test
    void testLoginAnotherDevice() {
        User user = new User("Nikola", "123");
        users.put("Nikola",user);
        loggedInUsers.add(user);

        Command login = new LogInCommand("Nikola", "123", loggedInUser, users, loggedInUsers);

        String response = login.execute();

        assertEquals("User is already logged in!", response,
                "Valid command should return \"User is already logged in!\" when user is in the structure!");

    }

    @Test
    void testSuccessfulLogin() {
        User user = new User("Nikola", "123");
        users.put("Nikola", user);

        Command login = new LogInCommand("Nikola", "123", null, users, loggedInUsers);

        String response = login.execute();

        assertEquals("Successful login!", response,
                "Should return success message upon correct credentials!");

        assertTrue(loggedInUsers.contains(user),
                "The user should be added to the loggedInUsers set after successful login!");

        assertEquals(1, loggedInUsers.size(),
                "There should be exactly one logged in user.");
    }

}
