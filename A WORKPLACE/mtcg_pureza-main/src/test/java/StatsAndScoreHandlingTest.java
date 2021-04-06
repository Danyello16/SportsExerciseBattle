
import MCTG.Users.StatsAndScoreHandler;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StatsAndScoreHandlingTest {
    @Test
    public void testShowStats_shouldReturnRightString() throws IOException {

        StatsAndScoreHandler StatsScoreTest = new StatsAndScoreHandler();

        List<String> headers = new ArrayList<String>();
        headers.add("User-Agent: curl/7.55.1");
        headers.add("Accept: */*");
        headers.add("Authorization: Basic kienboec-mtcgToken");

        String noMessage = "";
        assertFalse(StatsScoreTest.showStats(headers) == noMessage, "The returned String is not empty");
        assertTrue(StatsScoreTest.showStats(headers).contains("kienboec"), "The returned String contains kienboec");
    }

    @Test
    public void testShowScoreboard_shouldReturnRightString() throws IOException {

        StatsAndScoreHandler StatsScoreTest = new StatsAndScoreHandler();

        String noMessage = "";

        assertFalse(StatsScoreTest.showScoreboard() == noMessage, "The returned String is not empty");
        assertTrue(StatsScoreTest.showScoreboard().contains("kienboec"), "The returned String contains kienboec");
    }



    @Test
    public void testAnnounceStats_shouldReturnRightString() throws IOException {

        StatsAndScoreHandler StatsScoreTest = new StatsAndScoreHandler();

        List<String> headers = new ArrayList<String>();
        headers.add("User-Agent: curl/7.55.1");
        headers.add("Accept: */*");
        headers.add("Authorization: Basic kienboec-mtcgToken");

        String noMessage = "";
        assertFalse(StatsScoreTest.showStats(headers) == noMessage, "The returned String should contain kienboec or altonhof");
        assertTrue(StatsScoreTest.showStats(headers).contains("kienboec"), "The returned String contains kienboec");
    }

    @Test
    public void testMakeVisibleScoreboard_shouldReturnRightString() throws IOException {

        StatsAndScoreHandler StatsScoreTest = new StatsAndScoreHandler();

        String noMessage = "";

        assertFalse(StatsScoreTest.showScoreboard() == noMessage, "The returned String should contain kienboec or altonhof");
        assertTrue(StatsScoreTest.showScoreboard().contains("kienboec"), "The returned String contains kienboec");
    }
}


