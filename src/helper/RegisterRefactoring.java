package helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterRefactoring {

    private static final Pattern REGISTER_PATTERN =
            Pattern.compile("register --username=([^\\s]+) --password=([^\\s]+)");

    public static UserCredentials parseRegister(String input) {
        Matcher matcher = REGISTER_PATTERN.matcher(input);

        if (matcher.find()) {
            return new UserCredentials(matcher.group(1), matcher.group(2));
        }

        return null;
    }

}
