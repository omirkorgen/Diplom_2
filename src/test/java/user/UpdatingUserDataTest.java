package user;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

@DisplayName("Изменение данных пользователя")
public class UpdatingUserDataTest {
    private User user;
    private UserClient client = new UserClient();
    private UserChecks check = new UserChecks();
    private String accessToken;


    @Before
    @Step("Создание юзера")
    public void setUp(){
        user = User.generateUser();
        accessToken = check.checkCreated(client.createUser(user));
    }

    @After
    @Step("Удаление юзера по accessToken")
    public void deleteUser() {
        if(accessToken != null) {
            ValidatableResponse response = client.deleteUser(accessToken);
            String message = check.deletedSuccessfully(response);
            assert message.contains("User successfully removed");
        }
    }

    @Test
    @DisplayName("Успешное изменение имени")
    public void testSuccessfullyUpdateUserName(){
        var creds = UserCredentialsForUpdate.fromUser(user);
        creds.setName("Anton");
        ValidatableResponse response = client.updateUser(accessToken, creds);
        check.successfullyUpdatedData(response);
    }

    @Test
    @DisplayName("Успешное изменение email")
    public void testSuccessfullyUpdateUserEmail(){
        var creds = UserCredentialsForUpdate.fromUser(user);
        creds.setEmail("123"+user.getEmail());
        ValidatableResponse response = client.updateUser(accessToken, creds);
        check.successfullyUpdatedData(response);
    }

    @Test
    @DisplayName("Изменение имени без авторизации")
    public void testFailedUpdateUserName(){
        var creds = UserCredentialsForUpdate.fromUser(user);
        creds.setName("Anton");
        ValidatableResponse response = client.updateUser("", creds);
        check.failedUpdatedData(response);
    }

    @Test
    @DisplayName("Изменение email без авторизации")
    public void testFailedUpdateUserEmail(){
        var creds = UserCredentialsForUpdate.fromUser(user);
        creds.setEmail("123"+user.getEmail());
        ValidatableResponse response = client.updateUser("", creds);
        check.failedUpdatedData(response);
    }
}
