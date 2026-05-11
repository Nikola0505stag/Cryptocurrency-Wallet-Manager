package commands;

import data.User;

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

        loggedInUser.depositMoney(amount);
        return "Money deposited!";
    }

}
