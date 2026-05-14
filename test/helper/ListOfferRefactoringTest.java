package helper;

import exceptions.WrongListOfferCommandException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ListOfferRefactoringTest {

    @Test
    void testParsingListOfferInvalidCommand() {
        assertThrows(WrongListOfferCommandException.class, () -> ListOfferRefactoring.parseAssetId("list-offer"),
                "Change password command should be like \"list-offer=<name>\"!");
        assertThrows(WrongListOfferCommandException.class, () -> ListOfferRefactoring.parseAssetId("list-offer="),
                "Change password command should be like \"list-offer=<name>\"!");
        assertThrows(WrongListOfferCommandException.class, () -> ListOfferRefactoring.parseAssetId("list-offer BTC"),
                "Change password command should be like \"list-offer=<name>\"!");

    }

    @Test
    void testParsingListOfferCommandWithValidData() {
        assertDoesNotThrow(() -> ListOfferRefactoring.parseAssetId("list-offer=BTC"),
                "List offer parsing should't throw exception when command is okay!");

        String data = ListOfferRefactoring.parseAssetId("list-offer=BTC");

        assertEquals("BTC", data);

        data = ListOfferRefactoring.parseAssetId("list-offer=SOME_TEXT");
        assertEquals("SOME_TEXT", data);

    }

    @Test
    void testParsingListOfferCommandToUpperMethod() {
        String data = ListOfferRefactoring.parseAssetId("list-offer=eth");
        assertEquals("ETH", data, "Should normalize asset ID to upper case");
    }

    @Test
    void testParsingListOfferWithSpacesShouldFail() {
        // Ако искаш да си стриктен, тези трябва да хвърлят изключение
        assertThrows(WrongListOfferCommandException.class, () -> ListOfferRefactoring.parseAssetId(" list-offer=BTC"));
        assertThrows(WrongListOfferCommandException.class, () -> ListOfferRefactoring.parseAssetId("list-offer=BTC "));
    }
}
