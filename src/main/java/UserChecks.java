import io.restassured.response.ValidatableResponse;

import java.net.HttpURLConnection;

import static java.net.HttpURLConnection.*;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class UserChecks {

    public String checkLogin(ValidatableResponse response) {
        String accessToken = response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .extract()
                .path("accessToken");
        assertNotEquals(null, accessToken);
        return accessToken.substring(7);
    }

    public String checkCreated(ValidatableResponse response) {
        String accessToken = response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .extract()
                .path("accessToken");
        assertNotEquals(null, accessToken);
        return accessToken.substring(7);
    }

    public String creationTwoIdenticalUser(ValidatableResponse response) {
        return response.assertThat()
                .statusCode(HTTP_FORBIDDEN)
                .body("message", notNullValue())
                .extract()
                .path("message")
                ;
    }

    public String creationFailed(ValidatableResponse response) {
        return response.assertThat()
                .statusCode(HTTP_FORBIDDEN)
                .body("message", notNullValue())
                .extract()
                .path("message")
                ;
    }

    public String deletedSuccessfully(ValidatableResponse response) {
        return response.assertThat()
                .statusCode(HTTP_ACCEPTED)
                .body("message", notNullValue())
                .extract()
                .path("message");
    }

    public String loginFailedWithoutOneParameter(ValidatableResponse response) {
        return response.assertThat()
                .statusCode(HTTP_UNAUTHORIZED)
                .body("message", notNullValue())
                .extract()
                .path("message");
    }
}
