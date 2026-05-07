package helper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class LogInRefactoringTest {

    @Test
    void testParseLoginWithValidData() {
        String input = "login --username=Nikola --password=123";
        var result = LogInRefactoring.parseLogin(input);

        assertNotNull(result, "Result should not be null for valid input!");
        assertEquals("Nikola", result.username(), "Invalid username parsing!");
        assertEquals("123", result.password(), "Invalid password parsing!");
    }

    @Test
    void testParseLoginWithMissingFlag() {
        String input1 = "login --username=Nikola";
        String input2 = "login --password=123";

        var result1 = LogInRefactoring.parseLogin(input1);
        var result2 = LogInRefactoring.parseLogin(input2);

        assertNull(result1, "Result should be null when flag missing!");
        assertNull(result2, "Result should be null when flag missing!");
    }

    @Test
    void testParseLoginWrongCommand() {
        String input = "register --username=Nikola --password=123";

        var result = LogInRefactoring.parseLogin(input);

        assertNull(result, "Result should be null when command is wrong(not login)!");
    }

    @Test
    void testParseLoginWithSpecialCharacters() {
        String input = "login --username=Nikola --password=p@ss123!";
        var result = LogInRefactoring.parseLogin(input);

        assertEquals("p@ss123!", result.password());
    }
}
