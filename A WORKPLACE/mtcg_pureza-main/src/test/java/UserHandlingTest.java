import MCTG.Users.UserHandling;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserHandlingTest {

    @Test
    public void testRegristration_shouldBeInDatabase() throws IOException {

        UserHandling userTest = new UserHandling();

        String payload = "{\"Username\":\"AnotherUsername\", \"Password\":\"AnotherPassword\"}";
        assertTrue(userTest.registration(payload) == 0, "The new User should now be in the Database");
    }

    @Test
    public void testLogIn_shouldLoggedIn() throws IOException {
        UserHandling userTest = new UserHandling();

        String payload = "{\"Username\":\"AnotherUsername\", \"Password\":\"AnotherPassword\"}";
        assertTrue(userTest.login(payload) == 0, "The new User should now be Logged In");
    }

    @Test
    public void testToken_shouldGetFromHeader() throws IOException {
        UserHandling userTest = new UserHandling();

        String path = "/users/alec";
        List<String> headers = new ArrayList<String>();
        headers.add("User-Agent: curl/7.55.1");
        headers.add("Accept: */*");
        headers.add("Authorization: Basic alec-mtcgToken");
        assertTrue(userTest.checkTokenForUserData1(path,headers) == 0, "Token must be the same");
    }


}
