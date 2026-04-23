package commands;

import data.User;
import helper.MyJDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

public class RegisterCommand implements Command{

    private final String username;
    private final String password;
    private final Map<String, User> users;

    public RegisterCommand(String username, String password, Map<String, User> users) {
        this.username = username;
        this.password = password;
        this.users = users;
    }

    @Override
    public String execute() {
        if (users.containsKey(username)) {
            return "User with username " + username + " already exists.";
        }

        User newUser = new User(username, password);
        String sql = "INSERT INTO Users (username, password) VALUES (?, ?)";

        try (Connection conn = MyJDBC.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.executeUpdate();

            users.put(username, newUser);
            return "Successfully registered user " + username;

        } catch (SQLException e) {
            e.printStackTrace();
            return "Error: Could not register ussr in database";
        }
    }
}
