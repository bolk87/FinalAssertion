package utils;

public class Constants {
    public static final String BASE_URL = "https://innopolispython.onrender.com";
    public static final String DB_URL = "jdbc:postgresql://dpg-d3qvhjndiees73am0fig-a.oregon-postgres.render.com/dbinnopolis";
    public static final String DB_USER = "dbinnopolis_user";
    public static final String DB_PASSWORD = "qsN9l1XpkfpSpDqeRJCFcIsBu95b2y6Y";

    public static final String EMPLOYEE_PATH = BASE_URL+"/employee";
    public static final String EMPLOYEE_BY_ID_PATH = EMPLOYEE_PATH + "/{id}";
    public static final String EMPLOYEE_BY_NAME_PATH = EMPLOYEE_PATH + "/name/{name}";
    public static final String EMPLOYEES_PATH = BASE_URL+"/employees";
}