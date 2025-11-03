package db;


import model.Employee;
import model.EmployeeRequest;
import io.qameta.allure.Step;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository {

    @Step("Создаем нового сотрудника в БД")
    public Integer createEmployee(EmployeeRequest request) throws SQLException {
        String sql = "INSERT INTO employee (name, position, surname, city) VALUES (?, ?, ?, ?) RETURNING id";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, request.getName());
            stmt.setString(2, request.getPosition());
            stmt.setString(3, request.getSurname());
            stmt.setString(4, request.getCity());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
            return null;
        }
    }

    @Step("Удаляем сотрудника по его ID в БД")
    public void deleteEmployee(Integer id) throws SQLException {
        String sql = "DELETE FROM employee WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    @Step("Получаем информацию о сотруднике по его ID из БД")
    public Employee getEmployeeById(Integer id) throws SQLException {
        String sql = "SELECT * FROM employee WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Employee employee = new Employee();
                employee.setId(rs.getInt("id"));
                employee.setName(rs.getString("name"));
                employee.setPosition(rs.getString("position"));
                employee.setCity(rs.getString("city"));
                employee.setSurname(rs.getString("surname"));
                return employee;
            }
            return null;
        }
    }

    @Step("Получаем информацию о сотруднике по его имени в БД")
    public List<Employee> getEmployeesByName(String name) throws SQLException {
        String sql = "SELECT * FROM employee WHERE name = ?";
        List<Employee> employees = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Employee employee = new Employee();
                employee.setId(rs.getInt("id"));
                employee.setName(rs.getString("name"));
                employee.setPosition(rs.getString("position"));
                employee.setSurname(rs.getString("surname"));
                employee.setCity(rs.getString("city"));
                employees.add(employee);
            }
        }
        return employees;
    }
}