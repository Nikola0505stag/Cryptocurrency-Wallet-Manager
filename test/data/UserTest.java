package data;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    void testSetPasswordWithInvalidData() {
        User user = new User("Nikola", "1");

        assertThrows(IllegalArgumentException.class, ()-> {
            user.setPassword(null);
        }, "Null password should throw IllegalArgumentException!");
        assertThrows(IllegalArgumentException.class, () -> {
            user.setPassword(" ");
        }, "Blank password should throw IllegalArgumentException!");
    }

    @Test
    void testSetPasswordWithValidData() {
        User user = new User("Nikola", "1");

        assertDoesNotThrow(() -> {
            user.setPassword("2");
                }, "Not empty string shouldn't throw exception in setPassword()");

        user.setPassword("3");
        assertEquals("3", user.getPassword(), "SetPassword not working!");
    }

    @Test
    void testSetUsernameWithInvalidData() {
        User user = new User("Nikola", "1");

        assertThrows(IllegalArgumentException.class, ()-> {
            user.setUsername(null);
        }, "Null username should throw IllegalArgumentException!");
        assertThrows(IllegalArgumentException.class, () -> {
            user.setUsername(" ");
        }, "Blank username should throw IllegalArgumentException!");
    }

    @Test
    void testSetUsernameWithValidData() {
        User user = new User("Nikola", "1");

        assertDoesNotThrow(() -> {
            user.setUsername("NeNikola");
        }, "Not empty string shouldn't throw exception in setUsername()");

        user.setUsername("Something");
        assertEquals("Something", user.getUsername(), "SetUsername not working!");
    }

    @Test
    void testConstructorWithValidData() {
        User user = new User("Nikola", "Neychev");

        assertEquals("Nikola", user.getUsername(), "Username set in constructor not working!");
        assertEquals("Neychev", user.getPassword(), "Password set in constructor not working!");
        assertEquals(0, user.getBalance(), "The starting balance should be zero!");
        assertNotNull(user.getCrypto(), "Crypto collection shouldn't be null at the beginning!");
        assertTrue(user.getCrypto().isEmpty(), "Crypto collection should be empty ad the beginning!");
    }

    @Test
    void testConstructorWithInvalidData() {
        assertThrows(IllegalArgumentException.class, () -> {
            User user = new User(null, "1");
        }, "Null username in constructor should throw IllegalArgumentException!");

        assertThrows(IllegalArgumentException.class, () -> {
            User user = new User(" ", "1");
        }, "Blank username in constructor should throw IllegalArgumentException!");

        assertThrows(IllegalArgumentException.class, () -> {
            User user = new User("null", null);
        }, "Null password in constructor should throw IllegalArgumentException!");

        assertThrows(IllegalArgumentException.class, () -> {
            User user = new User("null", null);
        }, "Blank password in constructor should throw IllegalArgumentException!");
    }

    @Test
    void testDepositMoney() {
        User user = new User("Nikola", "1");

        assertThrows(IllegalArgumentException.class, () -> {
            user.depositMoney(0);
        }, "0 deposit should throw IllegalArgumentException!");

        assertThrows(IllegalArgumentException.class, () -> {
            user.depositMoney(-10);
        }, "Negative deposit should throw IllegalArgumentException!");

        user.depositMoney(10);
        assertEquals(10, user.getBalance(), "Deposit 10 must add the deposit in balance!");
    }

}
