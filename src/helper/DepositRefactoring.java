package helper;

import exceptions.WrongDepositCommandException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DepositRefactoring {

    private static final Pattern DEPOSIT_PATTERN =
            Pattern.compile("^deposit-money=(\\d+(\\.\\d{1,2})?)$");

    public static double parseAmount(String input) {
        Matcher matcher = DEPOSIT_PATTERN.matcher(input);

        if (matcher.matches()) {
            return Double.parseDouble(matcher.group(1));
        }

        throw new WrongDepositCommandException("Invalid deposit command! Format must be: deposit-money=<amount>");
    }
}