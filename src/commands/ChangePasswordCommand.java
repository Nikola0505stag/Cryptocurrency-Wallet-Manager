package commands;

import data.User;
import exceptions.WrongChangePasswordCommandException;
import helper.MyJDBC;

public class ChangePasswordCommand implements Command{

    private User loggedInUser;
    private String oldPassword;
    private String newPassword;

    public ChangePasswordCommand(User loggedInUser, String oldPassword, String newPassword) {
        this.loggedInUser = loggedInUser;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }


    @Override
    public String execute() {
        if (loggedInUser == null) {
            return "You are not logged in!";
        }

        String rightPassword = loggedInUser.getPassword();

        if (!rightPassword.equals(oldPassword)) {
            throw new WrongChangePasswordCommandException("The old password doesn't match the correct password!");
        }

        MyJDBC.updatePassword(loggedInUser.getUsername(), newPassword);
        loggedInUser.setPassword(newPassword);

        return "Change password ready!";

    }
}
