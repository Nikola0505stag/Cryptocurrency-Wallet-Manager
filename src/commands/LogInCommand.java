package commands;

import data.User;
import java.util.Map;
import java.util.Set;

public class LogInCommand implements Command{

    private final String username;
    private final String password;
    private User loggedInUser;
    private final Map<String, User> users;
    private final Set<User> loggedInUsers;

    public LogInCommand(String username, String password, User loggedInUser, Map<String, User> users, Set<User> loggedInUsers) {
        this.username = username;
        this.password = password;
        this.loggedInUser = null;
        this.users = users;
        this.loggedInUsers = loggedInUsers;
    }

    @Override
    public String execute() {
        if (loggedInUser != null) {
            return "You are already logged in!";
        }

        if (!users.containsKey(username)) {
            return "There isn't user with this name!";
        } else {
            String rightPassword = users.get(username).getPassword();

            if (!rightPassword.equals(password)) {
                return "Wrong password!";
            }
        }

        User currUser = users.get(username);

        if (loggedInUsers.contains(currUser)) {
            return "You are already logged in!";
        }

        loggedInUsers.add(currUser);
        return "Successful registration!";

    }

}
