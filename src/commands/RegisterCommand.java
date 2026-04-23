package commands;

import data.User;
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
        User newUser = new User(username, password);

        if (users.putIfAbsent(username, newUser) == null) {
            return "Successfully registered user " + username;
        }
        return "User with username " + username + " already exists.";
    }
}
