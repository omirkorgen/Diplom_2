package order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import java.util.List;

import static java.net.HttpURLConnection.*;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertTrue;


public class OrderChecks {

    @Step("Получение ответа об успешном создание заказа")
    public void successfulOrderCreation(ValidatableResponse response) {
        boolean created = response
                .assertThat()
                .statusCode(HTTP_OK)
                .extract()
                .path("success");
        assertTrue(created);
    }

    @Step("Получение ответа о создание заказа без ингредиентов")
    public String createOrderWithoutIngredient(ValidatableResponse response) {
        return response.assertThat()
                .statusCode(HTTP_BAD_REQUEST)
                .body("message", notNullValue())
                .extract()
                .path("message");
    }

    @Step("Получение ответа о создание заказа с неверным хешем ингредиентов")
    public void createOrderWithInvalidIngredient(ValidatableResponse response) {
                response.assertThat()
                .statusCode(HTTP_INTERNAL_ERROR);
    }

    @Step("Получение ответа о заказах авторизованного юзера")
    public List<String> getOrder(ValidatableResponse response) {
        return response.assertThat()
                .statusCode(HTTP_OK)
                .extract()
                .path("orders[0].ingredients");
    }

    @Step("Получение ответа о заказах неавторизованного юзера")
    public String getOrderWithoutAuth(ValidatableResponse response) {
        return response.assertThat()
                .statusCode(HTTP_UNAUTHORIZED)
                .body("message", notNullValue())
                .extract()
                .path("message");
    }
}
