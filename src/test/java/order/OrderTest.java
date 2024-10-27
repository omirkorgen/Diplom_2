package order;

import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import user.User;
import user.UserChecks;
import user.UserClient;

public class OrderTest {
    private OrderChecks orderChecks = new OrderChecks();
    private OrderRequest request = new OrderRequest();
    private UserClient client = new UserClient();
    private UserChecks userChecks = new UserChecks();
    private Order order;
    private User user;
    private String accessToken;

    @Before
    public void setUp(){
        user = User.generateUser();
        accessToken = userChecks.checkCreated(client.createUser(user));
    }

    @After
    public void deleteUser() {
        if(accessToken != null) {
            ValidatableResponse response = client.deleteUser(accessToken);
            String message = userChecks.deletedSuccessfully(response);
            assert message.contains("User successfully removed");
        }
    }

    @Test
    public void testSuccessfulOrderCreationWithAuth() {
        order = new Order(OrderData.INGREDIENT);
        ValidatableResponse response = request.createOrder(order, accessToken);
        orderChecks.successfulOrderCreation(response);
    }

    @Test
    public void testSuccessfulOrderCreationWithoutAuth() {
        order = new Order(OrderData.INGREDIENT);
        ValidatableResponse response = request.createOrder(order, "");
        orderChecks.successfulOrderCreation(response);
    }

    @Test
    public void testFailedCreateOrderWithoutIngredient() {
        order = new Order(null);
        ValidatableResponse response = request.createOrder(order, accessToken);
        String message = orderChecks.createOrderWithoutIngredient(response);
        assert message.contains("Ingredient ids must be provided");
    }

    @Test
    public void testFailedCreateOrderWithInvalidIngredient() {
        order = new Order(OrderData.INGREDIENT_WITH_WRONG_HASH);
        ValidatableResponse response = request.createOrder(order, accessToken);
        orderChecks.createOrderWithInvalidIngredient(response);
    }
}