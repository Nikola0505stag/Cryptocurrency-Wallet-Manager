package helper;

import data.User;
import org.junit.jupiter.api.*;
import java.sql.*;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MyJDBCTest {

    @AfterEach
    void setUp() throws SQLException {
        try (Connection conn = MyJDBC.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM Users WHERE username LIKE 'test_%'");
        }
    }

    @Test
    void testLoadUsersSuccess() throws SQLException {
        try (Connection conn = MyJDBC.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("INSERT INTO Users (username, password) VALUES ('test_ivan', 'ivan123')");
            stmt.executeUpdate("INSERT INTO Users (username, password) VALUES ('test_maria', 'maria456')");
        }

        Map<String, User> result = MyJDBC.loadUsers();

        assertNotNull(result);
        assertTrue(result.containsKey("test_ivan"), "Reading from the database not working!");
        assertTrue(result.containsKey("test_maria"), "Reading from the database not working!");
        assertEquals("ivan123", result.get("test_ivan").getPassword(), "Password not maching!");
    }

    @Test
    void testLoadUsersEmptyState() {
        Map<String, User> result = MyJDBC.loadUsers();

        assertNotNull(result);
    }

    @Test
    void testUpdateBalanceSuccess() throws SQLException {
        try (Connection conn = MyJDBC.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("INSERT INTO Users (username, password, balance) VALUES ('test_petyr', 'pass123', 10.0)");
        }

        MyJDBC.updateBalance("test_petyr", 50.50);

        Map<String, User> result = MyJDBC.loadUsers();
        assertEquals(60.50, result.get("test_petyr").getBalance(), 0.001, "The balance was not updated correctly!");
    }

    @Test
    void testLoadUsersIncludesBalance() throws SQLException {
        try (Connection conn = MyJDBC.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("INSERT INTO Users (username, password, balance) VALUES ('test_balance_user', 'p', 123.45)");
        }

        Map<String, User> result = MyJDBC.loadUsers();

        assertTrue(result.containsKey("test_balance_user"));
        assertEquals(123.45, result.get("test_balance_user").getBalance(), 0.001, "LoadUsers failed to fetch the balance column correctly!");
    }

    @Test
    void testUpdateBalanceNonExistentUser() {
        assertDoesNotThrow(() -> MyJDBC.updateBalance("non_existent_test_user", 100.0),
                "Updating balance for non-existent user should not throw an exception.");
    }

}