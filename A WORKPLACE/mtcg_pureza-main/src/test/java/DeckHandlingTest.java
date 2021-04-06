
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeckHandlingTest {

    @Test
    public void testCheckDeck_shouldBeInDatabase() throws IOException {

        //DeckHandler deckTest = new DeckHandler();

        List<String> headers = new ArrayList<String>();
        headers.add("User-Agent: curl/7.55.1");
        headers.add("Accept: */*");
        headers.add("Authorization: Basic kienboec-mtcgToken");

        //assertTrue(deckTest.checkDeck(headers) == 0, "The new User has a Deck in Database");
    }

    @Test
    public void testConfigureCardsInDeck_shouldHaveEnoughCards() throws IOException {

       // DeckHandler deckTest = new DeckHandler();

        List<String> headers = new ArrayList<String>();
        headers.add("User-Agent: curl/7.55.1");
        headers.add("Accept: */*");
        headers.add("Content-Type: application/json");
        headers.add("Authorization: Basic kienboec-mtcgToken");

        String payload = "[\"845f0dc7-37d0-426e-994e-43fc3ac83c08\", \"99f8f8dc-e25e-4a95-aa2c-782823f36e2a\", \"e85e3976-7c86-4d06-9a80-641c2019a79f\"]";

      //  assertTrue(deckTest.configureCardsInDeck(payload,headers) == 2, "The new User has not enough Cards");
    }

    @Test
    public void testShowCards_shouldBeInDatabase() throws IOException {

        //DeckHandler deckTest = new DeckHandler();

        List<String> headers = new ArrayList<String>();
        headers.add("User-Agent: curl/7.55.1");
        headers.add("Accept: */*");
        headers.add("Authorization: Basic kienboec-mtcgToken");

        String noMessage = "";
       // assertFalse(deckTest.showDeck(headers) == noMessage, "The returned String is not empty");
    }
}
