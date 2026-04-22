package server;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final int SERVER_PORT = 9904;

    static void main() {
        new Server().start();
    }

    private void start() {
        Thread.currentThread().setName("Server thread");

        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
             ExecutorService executor = Executors.newCachedThreadPool()){

            InetAddress serverAddress = Inet4Address.getLocalHost();
            System.out.println("Server started on" + serverAddress.getHostAddress() +
                    "and listening for connection requests on port" + SERVER_PORT);

            Socket clientSocket;

            while (true) {
                clientSocket = serverSocket.accept();
                System.out.println("Accepted connection request from client" + clientSocket.getInetAddress() + ":" +
                        clientSocket.getPort());

                //TODO: some thead + executor to execute thread!

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
