import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LoginUserTest {
    private User user;
    private String accessToken;
    private UserClient client = new UserClient();
    private UserChecks check = new UserChecks();

    @Before
    public void setUp(){
        user = User.generateUser();
        client.createUser(user);
    }

    @After
    public void deleteUser() {
        if(accessToken != null) {
            ValidatableResponse response = client.deleteUser(accessToken);
            String message = check.deletedSuccessfully(response);
            assert message.contains("User successfully removed");
        }
    }

    @Test
    public void testSuccessfullyLogin() {
        var creds = UserCredentials.fromUser(user);
        ValidatableResponse response = client.login(creds);
        accessToken = check.checkCreated(response);
    }

    @Test
    public void testLoginWithoutOneParameter() {
        var creds = UserCredentials.fromUser(user);
        creds.setPassword("");
        ValidatableResponse response = client.login(creds);
        String message = check.loginFailedWithoutOneParameter(response);
        assert message.contains("email or password are incorrect");
    }
}
