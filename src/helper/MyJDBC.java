package helper;

import data.User;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class MyJDBC {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/Register_schema?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "ValioValio";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static Map<String, User> loadUsers() {
        Map<String, User> users = new HashMap<>();
        String sql = "SELECT username, password, balance FROM Users";

        try (Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String u = rs.getString("username");
                String p = rs.getString("password");
                double b = rs.getDouble("balance");

                User user = new User(u, p);
                user.setBalance(b);

                users.put(u, user);
            }

        } catch (SQLException e) {
            System.out.println("Could not load users from DB!");
            e.printStackTrace();
        }

        return users;
    }

    public static void updateBalance(String username, double amount) {
        String sql = "UPDATE Users SET balance = balance + ? WHERE username = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, amount);
            pstmt.setString(2, username);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Could not update money");
            e.printStackTrace();
        }
    }
}