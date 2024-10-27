package user;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class UserClient {


    public static final String BASE_URI = "https://stellarburgers.nomoreparties.site";

    public ValidatableResponse createUser(User user) {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .body(user)
                .when()
                .post("/api/auth/register")
                .then();
    }

    public ValidatableResponse deleteUser(String accessToken) {
        return given()
                .contentType(ContentType.JSON)
                .auth().oauth2(accessToken)
                .baseUri(BASE_URI)
                .when()
                .delete("/api/auth/user")
                .then();
    }

    public ValidatableResponse login(UserCredentials userCredentials) {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .body(userCredentials)
                .when()
                .post("/api/auth/login")
                .then();
    }

    public ValidatableResponse updateUser(String accessToken, UserCredentialsForUpdate userCredentialsForUpdate) {
        return given()
                .contentType(ContentType.JSON)
                .auth().oauth2(accessToken)
                .baseUri(BASE_URI)
                .body(userCredentialsForUpdate)
                .when()
                .patch("/api/auth/user")
                .then();
    }
}
