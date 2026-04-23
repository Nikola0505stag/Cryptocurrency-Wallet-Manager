package helper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RegisterRefactoringTest {

    @Test
    void testParseRegisterWithValidData() {
        String input = "register --username=Nikola --password=123";
        var result = RegisterRefactoring.parseRegister(input);

        assertNotNull(result, "Result should not be null for valid input!");
        assertEquals("Nikola", result.username(), "Invalid username parsing!");
        assertEquals("123", result.password(), "Invalid password parsing!");
    }

    @Test
    void testParseRegisterWithMissingFlag() {
        String input1 = "register --username=Nikola";
        String input2 = "register --password=123";

        var result1 = RegisterRefactoring.parseRegister(input1);
        var result2 = RegisterRefactoring.parseRegister(input2);

        assertNull(result1, "Result should be null when flag missing!");
        assertNull(result2, "Result should be null when flag missing!");
    }

    @Test
    void testParseRegisterWrongCommand() {
        String input = "login --username=Nikola --password=123";

        var result = RegisterRefactoring.parseRegister(input);

        assertNull(result, "Result should be null when command is wrong(not register)!");
    }

    @Test
    void testParseRegisterWithSpecialCharacters() {
        String input = "register --username=Nikola --password=p@ss123!";
        var result = RegisterRefactoring.parseRegister(input);

        assertEquals("p@ss123!", result.password());
    }

}
