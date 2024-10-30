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
import java.util.List;
import static org.junit.Assert.assertEquals;

@DisplayName("Получение заказа")
public class GetOrderTest {
    private OrderChecks orderChecks = new OrderChecks();
    private OrderRequest request = new OrderRequest();
    private UserClient client = new UserClient();
    private UserChecks userChecks = new UserChecks();
    private Order order;
    private User user;
    private String accessToken;

    @Before
    @Step("Создание юзера и заказа")
    public void setUp(){
        user = User.generateUser();
        accessToken = userChecks.checkCreated(client.createUser(user));

        order = new Order(OrderData.INGREDIENT);
        ValidatableResponse response = request.createOrder(order, accessToken);
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
    @DisplayName("Получение заказа авторизованным юзером")
    public void getOrder() {
        ValidatableResponse response = request.getOrder(accessToken);
        List<String> actualIngredient = orderChecks.getOrder(response);
        assertEquals("The order ingredients differ", OrderData.INGREDIENT, actualIngredient);
    }

    @Test
    @DisplayName("Получение заказа неавторизованным юзером")
    public void getOrdersWithoutAuth() {
        ValidatableResponse response = request.getOrder("");
        String message = orderChecks.getOrderWithoutAuth(response);
        assert message.contains("You should be authorised");
    }
}
