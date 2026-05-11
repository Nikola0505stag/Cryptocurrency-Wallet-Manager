package commands;

import org.junit.jupiter.api.Test;
import data.User;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DepositCommandTest {

    @Test
    void testDepositWhenNoLoggedIn() {
        User user = null;
        double amount = 12;

        Command command = new DepositCommand(user, amount);

        assertEquals("You are not logged in!", command.execute(),
                "Deposit command should return \"You are not logged in!\" if you aren't logged in!");
    }

    @Test
    void testDepositWithValidData() {
        User user = new User("Nikola", "password");
        double amount = 12;

        Command command = new DepositCommand(user, amount);

        assertEquals("Money deposited!", command.execute(),
                "Deposit command should return \"Money deposited!\" when the deposit is ready!");
        assertEquals(12, user.getBalance());
    }
}
