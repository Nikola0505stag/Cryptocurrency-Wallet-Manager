package commands;

import java.io.IOException;

public class HelpCommand implements Command {

    @Override
    public String execute() throws IOException, InterruptedException {
        return "Register: \"register --username=<name> --password=<pass>\"" + "\n"
                + "Login: \"login --username=<name> --password=<pass>\"" + "\n"
                + "Deposit money: \"deposit-money=<amount>\"" + "\n"
                + "Print details: \"print --account-details\"" + "\n"
                + "Change password: \"change-password --old-password=<oldPass> --new_password=<newPass>\"" + "\n"
                + "List offers: \"list-offerings\"" + "\n"
                + "List offer: \"list-offer=<asset_id>\"" + "\n"
                + "Buy crypto: \"buy --offering=<asset_id> --money=<amount>\"" + "\n"
                + "Sell crypto: \"sell --offering=<asset_id>\"" + "\n"
                + "Wallet summary: \"get-wallet-summary\"" + "\n"
                + "Quit: \"quit\"" + "\n";
    }

}
