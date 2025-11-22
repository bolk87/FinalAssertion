package api;

import config.BaseApiTest;
import config.EmployeeApi;
import db.EmployeeRepository;
import io.qameta.allure.Epic;
import model.EmployeeRequest;
import org.junit.jupiter.api.*;
import utils.TestDataGenerator;

import java.sql.SQLException;

import static org.hamcrest.Matchers.*;
@Epic("Контрактные API автотесты")
@Disabled
public class EmployeeContractTests extends BaseApiTest {

    private EmployeeApi employeeApi;
    private EmployeeRepository employeeRepository;

    @BeforeEach
    public void setup() {
        employeeApi = new EmployeeApi();
        employeeRepository = new EmployeeRepository();
    }

    @Test
    @DisplayName("Проверка кода 201 в ответе при создании сотрудника")
    @Tags({@Tag("позитивный"), @Tag("контрактный-тест"), @Tag("api")})
    public void createEmployeeCode201(){
        EmployeeRequest request = TestDataGenerator.generateValidEmployeeRequest();
        employeeApi.createEmployee(request).
                then().
                statusCode(201);
    }


    @Test
    @DisplayName("Создание сотрудника")
    @Tags({@Tag("позитивный"), @Tag("контрактный-тест"), @Tag("api")})
    public void testCreateEmployeeContract() {
        EmployeeRequest request = TestDataGenerator.generateValidEmployeeRequest();

        employeeApi.createEmployee(request)
                .then()
                .statusCode(201)
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("Проверка get запроса по id сотрудника")
    @Tags({@Tag("позитивный"), @Tag("контрактный-тест"), @Tag("api")})
    public void testGetEmployeeByIdContract() throws SQLException {
        // Создание тестового сотрудника
        EmployeeRequest request = TestDataGenerator.generateValidEmployeeRequest();
        Integer createdEmployeeId = employeeRepository.createEmployee(request);

        employeeApi.getEmployeeById(createdEmployeeId)
                .then()
                .statusCode(200)
                .body("id", equalTo(createdEmployeeId));
    }

    @Test
    @DisplayName("Проверка тела ответа для get запроса")
    @Tags({@Tag("позитивный"), @Tag("контрактный-тест"), @Tag("api")})
    public void testValidateResponseBody() throws SQLException {
        // Создание тестового сотрудника
        EmployeeRequest request = TestDataGenerator.generateValidEmployeeRequest();
        Integer createdEmployeeId = employeeRepository.createEmployee(request);
        //проверка тела ответа
        employeeApi.getEmployeeById(createdEmployeeId)
                .then()
                .statusCode(200)
                .body("id", equalTo(createdEmployeeId))
                .body("name", equalTo(request.getName()))
                .body("position", equalTo(request.getPosition()))
                .body("surname", equalTo(request.getSurname()));
    }

    @Test
    @DisplayName("Проверка get запроса по имени сотрудника")
    @Tags({@Tag("позитивный"), @Tag("контрактный-тест"), @Tag("api")})
    public void testGetEmployeeByName () {
        //Создание нового сотрудника через API
        EmployeeRequest request = TestDataGenerator.generateValidEmployeeRequest();
        employeeApi.createEmployee(request)
                .then()
                .statusCode(201);
        //Проверяем, что можно найти сотрудника по имени
        employeeApi.getEmployeesByName(request.getName())
                .then()
                .statusCode(200)
                .body("name", equalTo(request.getName()));
    }

    @Test
    @DisplayName("Проверка get запроса для получения списка сотрудников")
    @Tags({@Tag("позитивный"), @Tag("контрактный-тест"), @Tag("api")})
    public void testGetEmployees () {
        employeeApi.getAllEmployees()
                .then()
                .statusCode(200)
                .extract()
                .path("id");
    }

    @Test
    @DisplayName("Проверка обновления сотрудника")
    @Tags({@Tag("позитивный"), @Tag("контрактный-тест"), @Tag("api")})
    public void testUpdateEmployee () {
        //Создание нового сотрудника через API
        employeeApi = new EmployeeApi();
        EmployeeRequest request = TestDataGenerator.generateValidEmployeeRequest();
        Integer apiEmployeeId = employeeApi.createEmployee(request)
                .then()
                .statusCode(201)
                .extract()
                .path("id");
        //Обновление данных о сотруднике через API
        EmployeeRequest updatedRequest = TestDataGenerator.generateValidEmployeeRequest();
        employeeApi.updateEmployee(apiEmployeeId, updatedRequest)
                .then()
                .statusCode(200)
                .body("id", equalTo(apiEmployeeId));
    }

    @Test
    @DisplayName("Удаление сотрудника")
    @Tags({@Tag("позитивный"), @Tag("контрактный-тест"), @Tag("api")})
    public void testDeleteEmployeeById () {
        //Создание нового сотрудника через API
        employeeApi = new EmployeeApi();
        EmployeeRequest request = TestDataGenerator.generateValidEmployeeRequest();
        Integer apiEmployeeId = employeeApi.createEmployee(request)
                .then()
                .statusCode(201)
                .extract()
                .path("id");
        //Удаление нового сотрудника через API
        employeeApi.deleteEmployee(apiEmployeeId)
                .then()
                .statusCode(200)
                .extract()
                .path("id");
    }
}