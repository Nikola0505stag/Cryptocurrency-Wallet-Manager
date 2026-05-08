package commands;

import data.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PrintAccountCommandTest {

    private User loggedInUser;

    @BeforeEach
    void setUp() {
        loggedInUser = null;
    }

    @Test
    void testNullLoggedInUser() {
        assertEquals(new PrintAccountCommand(loggedInUser).execute(), "You are not logged in!",
                "When there isn't logged in user command should return \"You are not logged in!\"");
    }

    @Test
    void testPrintAccountExecuteWithValidData() {
        String username = "Nikola";
        String password = "123";
        String balance = "0.0";

        User user = new User(username, password);
        String response = new PrintAccountCommand(user).execute();

        assertTrue(response.contains("Username: " + username));
        assertTrue(response.contains("Password: " + password));
        assertTrue(response.contains("Balance: " + balance));
    }
}
