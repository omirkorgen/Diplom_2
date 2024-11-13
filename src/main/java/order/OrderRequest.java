package order;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderRequest {
    public static final String BASE_URI = "https://stellarburgers.nomoreparties.site";

    @Step("Отправка запроса на создание заказа")
    public ValidatableResponse createOrder(Order order, String accessToken) {
        return given()
                .contentType(ContentType.JSON)
                .auth().oauth2(accessToken)
                .baseUri(BASE_URI)
                .body(order)
                .when()
                .post("/api/orders")
                .then();
    }

    @Step("Отправка запроса на получение заказа")
    public ValidatableResponse getOrder(String accessToken) {
        return given()
                .contentType(ContentType.JSON)
                .auth().oauth2(accessToken)
                .baseUri(BASE_URI)
                .when()
                .get("/api/orders")
                .then();
    }
}
