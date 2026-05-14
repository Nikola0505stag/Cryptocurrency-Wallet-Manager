package helper;

import data.Cryptocurrency;
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
            stmt.executeUpdate("DELETE FROM Crypto WHERE asset_id LIKE 'TEST_%'");
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

    @Test
    void testUpdatePasswordSuccess() throws SQLException {
        String username = "test_pass_user";
        String initialPass = "old_password";
        String newPass = "secret_new_123";

        try (Connection conn = MyJDBC.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(String.format(
                    "INSERT INTO Users (username, password, balance) VALUES ('%s', '%s', 0.0)",
                    username, initialPass));
        }

        MyJDBC.updatePassword(username, newPass);

        Map<String, User> users = MyJDBC.loadUsers();

        assertTrue(users.containsKey(username), "User should exist in DB");
        assertEquals(newPass, users.get(username).getPassword(), "The password in DB was not updated correctly!");
    }

    @Test
    void testUpdatePasswordForNonExistentUser() {
        assertDoesNotThrow(() -> MyJDBC.updatePassword("nobody_here", "some_pass"),
                "Updating password for non-existent user should not throw an exception.");
    }

    @Test
    void testUpdateAndLoadCryptoSuccess() {
        String id = "TEST_BTC";
        String name = "Test Bitcoin";
        double price = 60000.50;
        Cryptocurrency btc = new Cryptocurrency(id, name, price);

        MyJDBC.updateCrypto(btc);

        Map<String, Cryptocurrency> result = MyJDBC.loadCrypto();

        assertTrue(result.containsKey(id), "Crypto asset should be present in DB!");
        Cryptocurrency loaded = result.get(id);
        assertEquals(name, loaded.getName(), "Name mismatch!");
        assertEquals(price, loaded.getPrice_usd(), 0.001, "Price mismatch!");
        assertEquals(btc.getTimestamp(), loaded.getTimestamp(), "Timestamp was not saved correctly!");
    }

    @Test
    void testUpdateCryptoReplaceLogic() throws InterruptedException {
        String id = "TEST_ETH";
        Cryptocurrency ethV1 = new Cryptocurrency(id, "Ethereum", 2000.0);
        MyJDBC.updateCrypto(ethV1);

        Thread.sleep(10);

        double newPrice = 2100.0;
        Cryptocurrency ethV2 = new Cryptocurrency(id, "Ethereum", newPrice);

        MyJDBC.updateCrypto(ethV2);

        Map<String, Cryptocurrency> result = MyJDBC.loadCrypto();

        assertEquals(newPrice, result.get(id).getPrice_usd(), "The price was not updated (REPLACE failed)!");
        assertNotEquals(ethV1.getTimestamp(), result.get(id).getTimestamp(), "The timestamp should be updated to the latest one!");
    }

    @Test
    void testLoadCryptoEmptyState() {
        Map<String, Cryptocurrency> result = MyJDBC.loadCrypto();
        assertNotNull(result, "loadCrypto should return an empty map, not null!");
    }

}