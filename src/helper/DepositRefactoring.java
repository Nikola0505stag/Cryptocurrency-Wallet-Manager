package helper;

import exceptions.WrongDepositCommandException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DepositRefactoring {

    private static final Pattern DEPOSIT_PATTERN =
            Pattern.compile("^deposit-money=(-?\\d+(\\.\\d{1,2})?)$");

    public static double parseAmount(String input) {
        Matcher matcher = DEPOSIT_PATTERN.matcher(input);

        if (matcher.matches()) {
            double depositSum = Double.parseDouble(matcher.group(1));

            if (depositSum < 0) {

            } else if (depositSum == 0) {

            } else {
                return depositSum;
            }

        }

        throw new WrongDepositCommandException("Invalid deposit command! Format must be: deposit-money=<amount>");
    }
}