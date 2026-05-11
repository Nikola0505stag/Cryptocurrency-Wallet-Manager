package server;

import commands.*;
import data.User;
import exceptions.NegativeDepositException;
import exceptions.WrongDepositCommandException;
import exceptions.ZeroDepositException;
import helper.DepositRefactoring;
import helper.LogInRefactoring;
import helper.RegisterRefactoring;
import helper.UserCredentials;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;
import java.util.Set;

public class ClientRequestHandler implements Runnable{
    private final Socket socket;
    private final Map<String, User> users;
    private final Set<User> loggedInUsers;
    private User loggedInUser;

    public ClientRequestHandler(Socket socket, Map<String, User> users, Set<User> loggedInUsers) {
        this.socket = socket;
        this.users = users;
        this.loggedInUsers = loggedInUsers;
        loggedInUser = null;
    }


    @Override
    public void run() {
        Thread.currentThread().setName("Client handler for " + socket.getRemoteSocketAddress());

        try (socket;
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))){

            String message;


            while ((message = in.readLine()) != null) {
                Command command = null;
                
                if (message.equalsIgnoreCase("quit")) {
                    out.println("Bye bye!");
                    break;
                }

                if (message.startsWith("register")) {
                    UserCredentials creds = RegisterRefactoring.parseRegister(message);

                    if (creds != null) {
                        command = new RegisterCommand(creds.username(), creds.password(), users);
                        String result = command.execute();
                        out.println(result);
                    } else {
                        out.println("Invalid register format! Try again.");
                    }
                } else if (message.startsWith("login")) {
                    UserCredentials creds = LogInRefactoring.parseLogin(message);

                    if (creds != null) {
                        command = new LogInCommand(creds.username(), creds.password(), loggedInUser, users, loggedInUsers);
                        String result = command.execute();

                        if (result.equals("Successful login!")) {
                            loggedInUser = users.get(creds.username());
                        }

                        out.println(result);
                    } else {
                        out.println("Invalid login format! Try again.");
                    }
                } else if (message.equals("print --account-details")) {
                    command = new PrintAccountCommand(loggedInUser);
                    String result = command.execute();

                    out.println(result);
                } else if (message.startsWith("deposit")) {
                    try {
                        double amount = DepositRefactoring.parseAmount(message);
                        command = new DepositCommand(loggedInUser, amount);
                        String result = command.execute();

                        out.println(result);
                    } catch (WrongDepositCommandException | NegativeDepositException | ZeroDepositException e) {
                        out.println(e.getMessage());
                    }
                }

                if (command == null) {
                    out.println("Invalid command! Try again.");
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("There is a problem with the network communication", e);
        }
    }
}
