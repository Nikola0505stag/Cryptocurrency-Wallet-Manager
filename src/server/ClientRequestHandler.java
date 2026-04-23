package server;

import commands.Command;
import commands.RegisterCommand;
import data.User;
import helper.RegisterRefactoring;
import helper.UserCredentials;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;

public class ClientRequestHandler implements Runnable{
    private final Socket socket;
    private final Map<String, User> users;

    public ClientRequestHandler(Socket socket, Map<String, User> users) {
        this.socket = socket;
        this.users = users;
    }


    @Override
    public void run() {
        Thread.currentThread().setName("Client handler for " + socket.getRemoteSocketAddress());

        try (socket;
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))){

            String message;
            Command command = null;

            while ((message = in.readLine()) != null) {
                System.out.println("Client said: " + message);

                if (message.equalsIgnoreCase("quit")) {
                    out.println("Bye bye!");
                    break;
                }

                out.println("Echo from server: " + message);

                if (message.startsWith("register")) {
                    UserCredentials creds = RegisterRefactoring.parseRegister(message);

                    if (creds != null){
                        command = new RegisterCommand(creds.username(), creds.password(), users);
                        String result = command.execute();
                        out.println(result);
                    } else {
                        out.println("Invalid register format! Try again.");
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
