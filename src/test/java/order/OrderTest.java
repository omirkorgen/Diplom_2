package order;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import user.User;
import user.UserChecks;
import user.UserClient;

@DisplayName("Создание заказа")
public class OrderTest {
    private OrderChecks orderChecks = new OrderChecks();
    private OrderRequest request = new OrderRequest();
    private UserClient client = new UserClient();
    private UserChecks userChecks = new UserChecks();
    private Order order;
    private User user;
    private String accessToken;

    @Before
    @Step("Создание юзера")
    public void setUp(){
        user = User.generateUser();
        accessToken = userChecks.checkCreated(client.createUser(user));
    }

    @After
    @Step("Удаление юзера по accessToken")
    public void deleteUser() {
        if(accessToken != null) {
            ValidatableResponse response = client.deleteUser(accessToken);
            String message = userChecks.deletedSuccessfully(response);
            assert message.contains("User successfully removed");
        }
    }

    @Test
    @DisplayName("Создание заказа авторизованным юзером")
    public void testSuccessfulOrderCreationWithAuth() {
        order = new Order(OrderData.INGREDIENT);
        ValidatableResponse response = request.createOrder(order, accessToken);
        orderChecks.successfulOrderCreation(response);
    }

    @Test
    @DisplayName("Создание заказа неавторизованным юзером")
    public void testSuccessfulOrderCreationWithoutAuth() {
        order = new Order(OrderData.INGREDIENT);
        ValidatableResponse response = request.createOrder(order, "");
        orderChecks.successfulOrderCreation(response);
    }

    @Test
    @DisplayName("Создание заказа без ингредиентов")
    public void testFailedCreateOrderWithoutIngredient() {
        order = new Order(null);
        ValidatableResponse response = request.createOrder(order, accessToken);
        String message = orderChecks.createOrderWithoutIngredient(response);
        assert message.contains("Ingredient ids must be provided");
    }

    @Test
    @DisplayName("Создание заказа с неверным хешем ингредиентов")
    public void testFailedCreateOrderWithInvalidIngredient() {
        order = new Order(OrderData.INGREDIENT_WITH_WRONG_HASH);
        ValidatableResponse response = request.createOrder(order, accessToken);
        orderChecks.createOrderWithInvalidIngredient(response);
    }
}