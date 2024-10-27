package order;

import io.restassured.response.ValidatableResponse;

import static java.net.HttpURLConnection.*;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class OrderChecks {
    public void successfulOrderCreation(ValidatableResponse response) {
        boolean created = response
                .assertThat()
                .statusCode(HTTP_OK)
                .extract()
                .path("success");
        assertTrue(created);
    }
    public String createOrderWithoutIngredient(ValidatableResponse response) {
        return response.assertThat()
                .statusCode(HTTP_BAD_REQUEST)
                .body("message", notNullValue())
                .extract()
                .path("message");
    }
    public void createOrderWithInvalidIngredient(ValidatableResponse response) {
                response.assertThat()
                .statusCode(HTTP_INTERNAL_ERROR);
    }
}
