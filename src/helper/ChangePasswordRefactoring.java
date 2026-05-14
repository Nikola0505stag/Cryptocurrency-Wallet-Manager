package helper;

import exceptions.WrongChangePasswordCommandException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChangePasswordRefactoring {

    private static final Pattern CHANGE_PASSWORD_PATTERN =
            Pattern.compile("^change-password\\s--old-password=(\\S+)\\s--new-password=(\\S+)$");

    public static PasswordChangeDetails parseChangePassword(String input) {
        Matcher matcher = CHANGE_PASSWORD_PATTERN.matcher(input);

        if (matcher.matches()) {
            String oldPassword = matcher.group(1);
            String newPassword = matcher.group(2);

            return new PasswordChangeDetails(oldPassword, newPassword);
        }

        throw new WrongChangePasswordCommandException("Invalid change password command! \n" +
                " Format must be: \"change-password --old-password<pass> --new-password=<pass>\"");

    }

}
