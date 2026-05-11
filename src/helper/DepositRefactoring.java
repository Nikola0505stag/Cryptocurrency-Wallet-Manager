package helper;

import exceptions.NegativeDeposit;
import exceptions.WrongDepositCommandException;
import exceptions.ZeroDeposit;

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
                throw new NegativeDeposit("Can't deposit negative amount!");
            } else if (depositSum == 0) {
                throw new ZeroDeposit("Can't deposit zero amount!");
            } else {
                return depositSum;
            }

        }

        throw new WrongDepositCommandException("Invalid deposit command! Format must be: deposit-money=<amount>");
    }
}