package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientRequestHandler implements Runnable{
    private final Socket socket;

    public ClientRequestHandler(Socket socket) {
        this.socket = socket;
    }


    @Override
    public void run() {
        Thread.currentThread().setName("Client handler for " + socket.getRemoteSocketAddress());

        try (socket;
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))){

            String message;

            while ((message = in.readLine()) != null) {
                System.out.println("Client said: " + message);

                if (message.equalsIgnoreCase("quit")) {
                    out.println("Bye bye!");
                    break;
                }

                // later for methods from user class
                out.println("Echo from server: " + message);
            }

        } catch (IOException e) {
            throw new RuntimeException("There is a problem with the network communication", e);
        }
    }
}
