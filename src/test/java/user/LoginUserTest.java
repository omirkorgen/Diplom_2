package user;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

@DisplayName("Логин Пользователя")
public class LoginUserTest {
    private User user;
    private String accessToken;
    private UserClient client = new UserClient();
    private UserChecks check = new UserChecks();

    @Before
    @Step("Создание юзера")
    public void setUp(){
        user = User.generateUser();
        client.createUser(user);
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
    @DisplayName("Успешный логин")
    public void testSuccessfullyLogin() {
        var creds = UserCredentials.fromUser(user);
        ValidatableResponse response = client.login(creds);
        accessToken = check.checkLogin(response);
    }

    @Test
    @DisplayName("Логин без пароля")
    public void testLoginWithoutOneParameter() {
        var creds = UserCredentials.fromUser(user);
        creds.setPassword("");
        ValidatableResponse response = client.login(creds);
        String message = check.loginFailedWithoutOneParameter(response);
        assert message.contains("email or password are incorrect");
    }
}
