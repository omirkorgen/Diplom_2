package user;

import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UpdatingUserDataTest {
    private User user;
    private UserClient client = new UserClient();
    private UserChecks check = new UserChecks();
    private String accessToken;

    @Before
    public void setUp(){
        user = User.generateUser();
        accessToken = check.checkCreated(client.createUser(user));
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
    public void testSuccessfullyUpdateUserName(){
        var creds = UserCredentialsForUpdate.fromUser(user);
        creds.setName("Anton");
        ValidatableResponse response = client.updateUser(accessToken, creds);
        check.successfullyUpdatedData(response);
    }

    @Test
    public void testSuccessfullyUpdateUserEmail(){
        var creds = UserCredentialsForUpdate.fromUser(user);
        creds.setEmail("123"+user.getEmail());
        ValidatableResponse response = client.updateUser(accessToken, creds);
        check.successfullyUpdatedData(response);
    }

    @Test
    public void testFailedUpdateUserName(){
        var creds = UserCredentialsForUpdate.fromUser(user);
        creds.setName("Anton");
        ValidatableResponse response = client.updateUser("", creds);
        check.failedUpdatedData(response);
    }

    @Test
    public void testFailedUpdateUserEmail(){
        var creds = UserCredentialsForUpdate.fromUser(user);
        creds.setEmail("123"+user.getEmail());
        ValidatableResponse response = client.updateUser("", creds);
        check.failedUpdatedData(response);
    }
}
