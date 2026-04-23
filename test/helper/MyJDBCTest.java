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

}