package helper;

import java.sql.*;

public class MyJDBC {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/Register_schema?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "ValioValio";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}