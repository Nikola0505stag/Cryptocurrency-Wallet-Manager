package commands;

import data.User;

public class PrintAccountCommand implements Command {

    private User loggedInUser;

    public PrintAccountCommand(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    @Override
    public String execute() {
        if (loggedInUser == null) {
            return "You are not logged in!";
        }

        String username = loggedInUser.getUsername();
        String password = loggedInUser.getPassword();
        double balance = loggedInUser.getBalance();

        return "Username: " + username + "\n" +
                "Password: " + password + "\n" +
                "Balance: " + balance;

    }
}
