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
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegisterCommandTest {
    private Map<String, User> users;

    @BeforeEach
    void setUp() {
        users = new ConcurrentHashMap<>();
    }

    @Test
    void testExecuteSuccessfulRegistration() {
        Command command = new RegisterCommand("Nikola", "123", users);

        String response = command.execute();

        assertTrue(response.contains("Successfully registered"), "Valid command should return Successfully registered message!");
        assertTrue(users.containsKey("Nikola"), "Valid command should add the user in the map!");
        assertEquals("123", users.get("Nikola").getPassword(), "Password should match!");
    }

    @Test
    void testExecuteDuplicateRegistration() {
        users.put("Nikola", new User("Nikola", "old_pass"));

        RegisterCommand command = new RegisterCommand("Nikola", "new_pass", users);
        String response = command.execute();

        assertTrue(response.contains("already exists"), "Response should indicate duplicate user");
        assertEquals("old_pass", users.get("Nikola").getPassword(), "Original password should not be overwritten");
    }

    @Test
    void testRegisterSavesToDatabase() throws SQLException {
        String testUser = "TestUser" + System.currentTimeMillis();
        RegisterCommand command = new RegisterCommand(testUser, "pass123", users);

        String response = command.execute();
        assertTrue(response.contains("Successfully registered"));

        try (Connection conn = MyJDBC.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Users WHERE username = ?")) {

            pstmt.setString(1, testUser);
            ResultSet rs = pstmt.executeQuery();

            assertTrue(rs.next(), "The user must be in the data base");
            assertEquals("pass123", rs.getString("password"));
        }
    }

    @Test
    void testRegisterFailureOnDuplicateInDB() {
        String testUser = "DuplicateDBUser";
        new RegisterCommand(testUser, "pass1", users).execute();

        RegisterCommand command = new RegisterCommand(testUser, "pass2", users);
        String response = command.execute();

        assertTrue(response.contains("already exists"), "Not working with occupied username");
    }
}
