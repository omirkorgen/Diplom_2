package order;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import user.UserCredentials;

import static io.restassured.RestAssured.given;

public class OrderRequest {
    public static final String BASE_URI = "https://stellarburgers.nomoreparties.site";

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
}
