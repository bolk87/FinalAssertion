package api;

import config.BaseApiTest;
import config.EmployeeApi;
import db.EmployeeRepository;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import model.Employee;
import model.EmployeeRequest;


import org.junit.jupiter.api.*;
import utils.TestDataGenerator;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
@Epic("API автотесты для проверки бизнес-логики")
@Disabled
public class EmployeeBusinessLogicTests extends BaseApiTest {

    private EmployeeApi employeeApi;
    private EmployeeRepository employeeRepository;

    @Test
    @DisplayName("e2e создания нового сотрудника через апи метод")
    @Severity(SeverityLevel.CRITICAL)
    @Tags({@Tag("позитивный"), @Tag("бизнес-сценарий"), @Tag("api")})
    public void testCreateEmployeeAndVerifyInDatabase() throws SQLException {
        employeeApi = new EmployeeApi();
        employeeRepository = new EmployeeRepository();

        EmployeeRequest request = TestDataGenerator.generateValidEmployeeRequest();

        // Создание сотрудника через API
        Integer apiEmployeeId = employeeApi.createEmployee(request)
                .then()
                .statusCode(201)
                .extract()
                .path("id");

        // Проверка нового сотрудника в БД
        Employee dbEmployee = employeeRepository.getEmployeeById(apiEmployeeId);
        assertNotNull(dbEmployee);
        assertEquals(request.getName(), dbEmployee.getName());
        assertEquals(request.getPosition(), dbEmployee.getPosition());
        assertEquals(request.getSurname(), dbEmployee.getSurname());
        assertEquals(request.getCity(), dbEmployee.getCity());

        // Очистка БД
        employeeRepository.deleteEmployee(apiEmployeeId);
    }

    @Test
    @DisplayName("e2e тест обновления информации о сотруднике через апи метод")
    @Severity(SeverityLevel.CRITICAL)
    @Tags({@Tag("позитивный"), @Tag("бизнес-сценарий"), @Tag("api")})
    public void testUpdateEmployeeSalary() throws SQLException {
        employeeApi = new EmployeeApi();
        employeeRepository = new EmployeeRepository();

        // Создание сотрудника в БД
        EmployeeRequest createRequest = TestDataGenerator.generateValidEmployeeRequest();
        Integer employeeId = employeeRepository.createEmployee(createRequest);

        // Обновление информации о сотруднике через API
        EmployeeRequest updateRequest = new EmployeeRequest();
        updateRequest.setName(createRequest.getName());
        updateRequest.setPosition(createRequest.getPosition());
        updateRequest.setCity(createRequest.getCity());
        updateRequest.setSurname(createRequest.getSurname());

        employeeApi.updateEmployee(employeeId, updateRequest)
                .then()
                .statusCode(200);

        // Проверка обновленных данных в БД
        Employee updatedEmployee = employeeRepository.getEmployeeById(employeeId);
        assertEquals("Forth Worth", updatedEmployee.getCity());

        // Очистка - удаление созданного сотрудника в БД
        employeeRepository.deleteEmployee(employeeId);
    }
}