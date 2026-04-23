package commands;

import data.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
}
