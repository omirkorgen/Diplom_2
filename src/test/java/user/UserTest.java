package user;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;

@DisplayName("Создание пользователя")
public class UserTest {

    private UserClient client = new UserClient();
    private UserChecks check = new UserChecks();
    private String accessToken;

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
    @DisplayName("Успешное создание юзера")
    public void testCreateUser() {
        var user = User.generateUser();
        ValidatableResponse createResponse = client.createUser(user);
        accessToken = check.checkCreated(createResponse);
    }

    @Test
    @DisplayName("Создание двух одинаковых юзеров")
    public void testCreateTwoIdenticalUser() {
        var user = User.generateUser();
        ValidatableResponse createResponse = client.createUser(user);
        accessToken = check.checkCreated(createResponse);

        ValidatableResponse createResponse2 = client.createUser(user);
        String message = check.creationTwoIdenticalUser(createResponse2);
        assert message.contains("User already exists");
    }

    @Test
    @DisplayName("Создание юзера без одного обязательно параметра")
    public void testCreationFails() {
        var user = User.generateUser();
        user.setPassword(null);

        ValidatableResponse createResponce = client.createUser(user);
        String message = check.creationFailed(createResponce);
        assert message.contains("Email, password and name are required fields");
    }

}