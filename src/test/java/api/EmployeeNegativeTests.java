package api;

import config.BaseApiTest;
import config.EmployeeApi;
import db.EmployeeRepository;
import io.qameta.allure.Epic;
import model.EmployeeRequest;
import org.junit.jupiter.api.*;
import utils.TestDataGenerator;

import java.sql.SQLException;

import static org.hamcrest.Matchers.equalTo;
@Epic("Негативные API автотесты")
@Disabled
public class EmployeeNegativeTests extends BaseApiTest {

    private EmployeeApi employeeApi;

    @Test
    @DisplayName("Проверка get запроса с несуществующим id сотрудника")
    @Tags({@Tag("негативный"), @Tag("контрактный-тест")})
    public void testGetNonExistentEmployee() {
        employeeApi = new EmployeeApi();

        employeeApi.getEmployeeById(999999)
                .then()
                .statusCode(404);
    }

    @Test
    @DisplayName("Создание сотрудника с невалидными данными")
    @Tags({@Tag("негативный"), @Tag("контрактный-тест"), @Tag("api")})
    public void testCreateEmployeeWithInvalidData() {
        employeeApi = new EmployeeApi();

        EmployeeRequest invalidRequest = TestDataGenerator.generateInvalidEmployeeRequest();

        employeeApi.createEmployee(invalidRequest)
                .then()
                .statusCode(400);
    }

    @Test
    @DisplayName("Проверка удаление сотрудника, который уже был удален")
    @Tags({@Tag("негативный"), @Tag("контрактный-тест"), @Tag("api")})
    public void testDeleteAlreadyDeletedEmployee() throws SQLException {
        employeeApi = new EmployeeApi();
        EmployeeRepository repository = new EmployeeRepository();

        // Создание и удаление сотрудника
        EmployeeRequest request = TestDataGenerator.generateValidEmployeeRequest();
        Integer employeeId = repository.createEmployee(request);
        repository.deleteEmployee(employeeId);

        // Попытка удалить еще раз
        employeeApi.deleteEmployee(employeeId)
                .then()
                .statusCode(404);
    }

    @Test
    @DisplayName("Обновление сотрудника с несуществующим id сотрудника")
    @Tags({@Tag("негативный"), @Tag("контрактный-тест"), @Tag("api")})
    public void testUpdateNonExistentEmployee() {
        employeeApi = new EmployeeApi();

        EmployeeRequest updatedRequest = TestDataGenerator.generateValidEmployeeRequest();
        employeeApi.updateEmployee(999999, updatedRequest)
                .then()
                .statusCode(404);
    }

    @Test
    @DisplayName("Обновление сотрудника с невалидными данными")
    @Tags({@Tag("негативный"), @Tag("контрактный-тест"), @Tag("api")})
    public void testUpdateEmployeeWithInvalidData() {
        employeeApi = new EmployeeApi();
        //Создание нового сотрудника через API
        EmployeeRequest request = TestDataGenerator.generateValidEmployeeRequest();
        Integer apiEmployeeId = employeeApi.createEmployee(request)
                .then()
                .statusCode(201)
                .extract()
                .path("id");
        EmployeeRequest updatedRequest = TestDataGenerator.generateInvalidEmployeeRequest();
        employeeApi.updateEmployee(apiEmployeeId, updatedRequest)
                .then()
                .statusCode(400);
    }

    @Test
    @DisplayName("Проверка ошибки 404 при поиске несуществующего сотрудника")
    @Tags({@Tag("негативный"), @Tag("контрактный-тест"), @Tag("api")})
    public void testGetEmployeeByName () {
        employeeApi = new EmployeeApi();
        employeeApi.getEmployeesByName("Nepal")
                .then()
                .statusCode(404);
    }
}