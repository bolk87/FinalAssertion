package config;

import model.EmployeeRequest;
import utils.Constants;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;


public class EmployeeApi {
    private final BaseApiTest baseApi = new BaseApiTest();

    @Step("Создаем нового сотрудника")
    public Response createEmployee(EmployeeRequest request) {
        String token = BaseApiTest.getToken("admin", "admin");

        return baseApi.given()
                .body(request)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token).
                when().
                post(Constants.EMPLOYEE_PATH);
    }

    @Step("Получаем информацию о сотруднике по его ID")
    public Response getEmployeeById(Integer id) {
        return baseApi.given()
                .pathParam("id", id)
                .get(Constants.EMPLOYEE_BY_ID_PATH);
    }

    @Step("Получаем информацию о сотруднике по его имени")
    public Response getEmployeesByName(String name) {
        return baseApi.given()
                .pathParam("name", name)
                .get(Constants.EMPLOYEE_BY_NAME_PATH);
    }

    @Step("Удаляем сотрудника по его ID")
    public Response deleteEmployee(Integer id) {
        return baseApi.given()
                .pathParam("id", id)
                .delete(Constants.EMPLOYEE_BY_ID_PATH);
    }

    @Step("Обновляем информацию о сотруднкие по его ID")
    public Response updateEmployee(Integer id, EmployeeRequest request) {
        String token = BaseApiTest.getToken("admin", "admin");
        return baseApi.given()
                .pathParam("id", id)
                .body(request)
                .header("Authorization", "Bearer " + token)
                .put(Constants.EMPLOYEE_BY_ID_PATH);
    }

    @Step("Получаем список сотрудников")
    public Response getAllEmployees() {
        return baseApi.given()
                .get(Constants.EMPLOYEES_PATH);
    }
}