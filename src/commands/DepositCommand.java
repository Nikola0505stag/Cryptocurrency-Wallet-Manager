package commands;

import data.User;
import helper.MyJDBC;

public class DepositCommand implements Command{

    private User loggedInUser;
    private double amount;

    public DepositCommand(User loggedInUser, double amount) {
        this.loggedInUser = loggedInUser;
        this.amount = amount;
    }

    @Override
    public String execute() {
        if (loggedInUser == null) {
            return "You are not logged in!";
        }

        MyJDBC.updateBalance(loggedInUser.getUsername(), amount);

        loggedInUser.depositMoney(amount);
        return "Money deposited!";
    }

}
