import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import static io.restassured.RestAssured.given;

public class UserTest {
    private UserClient client = new UserClient();
    private UserChecks check = new UserChecks();

    @Test
    public void testCreateUser() {
        var user = User.generateUser();
        ValidatableResponse createResponse = client.createUser(user);
        check.checkCreated(createResponse);
    }

    @Test
    public void testCreateTwoIdenticalUser() {
        var user = User.generateUser();
        ValidatableResponse createResponse = client.createUser(user);
        check.checkCreated(createResponse);

        ValidatableResponse createResponse2 = client.createUser(user);
        String message = check.creationTwoIdenticalUser(createResponse2);
        assert message.contains("User already exists");
    }

    @Test
    public void testCreationFails() {
        var user = User.generateUser();
        user.setPassword(null);

        ValidatableResponse createResponce = client.createUser(user);
        String message = check.creationFailed(createResponce);
        assert message.contains("Email, password and name are required fields");
    }

}