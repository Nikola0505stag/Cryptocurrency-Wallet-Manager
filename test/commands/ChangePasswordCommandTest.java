package commands;

import data.User;
import exceptions.WrongChangePasswordCommandException;
import helper.MyJDBC;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class ChangePasswordCommandTest {

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User("test_user", "initial123");
    }

    @AfterEach
    void tearDown() throws SQLException {
        try (Connection conn = MyJDBC.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM Users WHERE username LIKE 'test_%'");
        }
    }

    @Test
    void testExecuteSuccess() {
        ChangePasswordCommand command = new ChangePasswordCommand(testUser, "initial123", "new_secret_pass");

        String result = command.execute();

        assertEquals("Change password ready!", result);
        assertEquals("new_secret_pass", testUser.getPassword(), "The user object should have the new password updated!");
    }

    @Test
    void testExecuteWrongOldPasswordThrowsException() {
        ChangePasswordCommand command = new ChangePasswordCommand(testUser, "wrong_pass", "new_pass");

        assertThrows(WrongChangePasswordCommandException.class, () -> {
            command.execute();
        }, "Should throw exception when old password doesn't match!");

        assertEquals("initial123", testUser.getPassword());
    }

    @Test
    void testExecuteUserNotLoggedIn() {
        ChangePasswordCommand command = new ChangePasswordCommand(null, "any", "any");

        String result = command.execute();

        assertEquals("You are not logged in!", result);
    }
}