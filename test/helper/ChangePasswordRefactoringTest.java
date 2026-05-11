package helper;

import exceptions.WrongChangePasswordCommandException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ChangePasswordRefactoringTest {

    @Test
    void testParsingChangePasswordCommandInvalidCommand() {
        assertThrows(WrongChangePasswordCommandException.class, () -> ChangePasswordRefactoring.parseChangePassword("change-password"),
                "Change password command should be like \"change-password --old-password=<pass> --new-password=<pass>\"!");
        assertThrows(WrongChangePasswordCommandException.class, () -> ChangePasswordRefactoring.parseChangePassword("change-password --newpassword= --oldpassword="),
                "Change password command should be like \"change-password --old-password=<pass> --new-password=<pass>\"!");
        assertThrows(WrongChangePasswordCommandException.class, () -> ChangePasswordRefactoring.parseChangePassword("change-password  --new-password=1 --old-password=3"),
                "Change password command should be like \"change-password --old-password=<pass> --new-password=<pass>\"!");

    }

    @Test
    void testParsingChangePasswordCommandWithValidData() {
        assertDoesNotThrow(() -> ChangePasswordRefactoring.parseChangePassword("change-password --old-password=123 --new-password=2"),
                "Change password parsing should't throw exception when command is okay!");

        var data = ChangePasswordRefactoring.parseChangePassword("change-password --old-password=123 --new-password=2");

        assertEquals("123", data.oldPassword(), "Invalid parsing method!");
        assertEquals("2", data.newPassword(), "Invalid parsing method!");

        data = ChangePasswordRefactoring.parseChangePassword("change-password --old-password=nikola --new-password=NIKOLA");

        assertEquals("nikola", data.oldPassword(), "Invalid parsing method!");
        assertEquals("NIKOLA", data.newPassword(), "Invalid parsing method!");
    }

    @Test
    void testParsingChangePasswordEdgeCases() {
        assertThrows(WrongChangePasswordCommandException.class,
                () -> ChangePasswordRefactoring.parseChangePassword("change-password --old-password= --new=123"),
                "Should fail when old password is empty");

        assertThrows(WrongChangePasswordCommandException.class,
                () -> ChangePasswordRefactoring.parseChangePassword("change-password -old-password=123 --new-password=456"),
                "Should fail with single dash instead of double");

        var data = ChangePasswordRefactoring.parseChangePassword("change-password --old-password=P@ss123 --new-password=N€wP@ss!#");
        assertEquals("P@ss123", data.oldPassword());
        assertEquals("N€wP@ss!#", data.newPassword());
    }

}
