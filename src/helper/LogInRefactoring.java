package helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogInRefactoring {

    private static final Pattern LOGIN_PATTERN =
            Pattern.compile("login --username=([^\\s]+) --password=([^\\s]+)");

    public static UserCredentials parseLogin(String input) {
        Matcher matcher = LOGIN_PATTERN.matcher(input);

        if (matcher.find()) {
            return new UserCredentials(matcher.group(1), matcher.group(2));
        }

        return null;
    }

}
