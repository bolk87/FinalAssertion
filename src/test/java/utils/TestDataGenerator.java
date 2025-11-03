package utils;


import model.EmployeeRequest;
import io.qameta.allure.Step;

public class TestDataGenerator {

    @Step("Генерируем валидные данные о сотруднике")
    public static EmployeeRequest generateValidEmployeeRequest() {
        EmployeeRequest request = new EmployeeRequest();
        request.setName("Test Employee " + System.currentTimeMillis());
        request.setPosition("QA");
        request.setSurname("Bolkunova");
        request.setCity("Forth Worth");
        return request;
    }

    @Step("Генерируем пустые данные о сотруднике")
    public static EmployeeRequest generateInvalidEmployeeRequest() {
        EmployeeRequest request = new EmployeeRequest();
        // Пустые обязательные поля
        request.setName("");
        request.setPosition("");
        request.setSurname("");
        request.setCity("");
        return request;
    }
}