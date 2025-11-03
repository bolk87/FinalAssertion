package config;

import model.User;
import utils.Constants;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;


public class BaseApiTest {

    @Step("Создаем апи запрос")
    @BeforeEach
    public void setup() {
        RestAssured.baseURI = Constants.BASE_URL;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Step("Заполняем параметр ContentType")
    protected RequestSpecification given() {
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON);
    }

    @Step("Получаем токен для авторизации через апи")
    public static String getToken(String username, String password) {
        RestAssured.baseURI = Constants.BASE_URL;

        return RestAssured.given().
                body(new User(username, password)).contentType(ContentType.JSON).
                when().
                post("/login").jsonPath().getString("token");
    }
}