package helper;

import exceptions.WrongListOfferCommandException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ListOfferRefactoring {
    private static final Pattern LIST_OFFER_PATTERN =
            Pattern.compile("^list-offer=(\\S+)$");

    public static String parseAssetId(String input) {
        Matcher matcher = LIST_OFFER_PATTERN.matcher(input);

        if (matcher.matches()) {
            String cryptoName = matcher.group(1);

            return cryptoName.toUpperCase();
        }

        throw new WrongListOfferCommandException("Invalid list offer command! \n" +
                " Format must be: \"list-offer=<name>\"");

    }
}
