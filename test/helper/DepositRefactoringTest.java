package helper;

import exceptions.NegativeDepositException;
import exceptions.WrongDepositCommandException;
import exceptions.ZeroDepositException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DepositRefactoringTest {

    @Test
    void testParsingWithInvalidDepositCommand() {
        assertThrows(WrongDepositCommandException.class, () -> DepositRefactoring.parseAmount("deposit-money==123=2342=23454=324"),
                "Deposit command shoult be like deposit-money=<amount>");
        assertThrows(WrongDepositCommandException.class, () -> DepositRefactoring.parseAmount("deposit=4"),
                "Deposit command shoult be like deposit-money=<amount>");
        assertThrows(WrongDepositCommandException.class, () -> DepositRefactoring.parseAmount("deposit-money="),
                "Deposit command shoult be like deposit-money=<amount>");

    }

    @Test
    void testParsingWithNegativeDepositAmount() {
        assertThrows(NegativeDepositException.class, () -> DepositRefactoring.parseAmount("deposit-money=-1"),
                "Negative deposit amount should throw NegativeDepositException!");
        assertThrows(NegativeDepositException.class, () -> DepositRefactoring.parseAmount("deposit-money=-113"),
                "Negative deposit amount should throw NegativeDepositException!");
    }

    @Test
    void testParsingWithZeroDepositAmount() {
        assertThrows(ZeroDepositException.class, () -> DepositRefactoring.parseAmount("deposit-money=0"),
                "Zero deposit amount should throw ZeroDepositException!");
    }

    @Test
    void testParsingWithValidData() {
        assertDoesNotThrow(() -> DepositRefactoring.parseAmount("deposit-money=123"),
                "Valid deposit command shouldn't throw exception!");

        assertEquals(123, DepositRefactoring.parseAmount("deposit-money=123"),
                "Invalid parsing!");
        assertEquals(123.12, DepositRefactoring.parseAmount("deposit-money=123.12"),
                "Invalid parsing!");
        assertEquals(321.99, DepositRefactoring.parseAmount("deposit-money=321.99"),
                "Invalid parsing!");
        assertEquals(0.82, DepositRefactoring.parseAmount("deposit-money=0.82"),
                "Invalid parsing!");
    }

}
