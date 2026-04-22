package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private static final int SERVER_PORT = 9904;

    static void main() {
        try (Socket socket = new Socket("localhost", SERVER_PORT);
             Scanner scanner = new Scanner(System.in);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))){

            System.out.println("Connected to server.");

            Listener listener = new Listener(in);

            listener.setDaemon(true);
            listener.start();

            while (true) {
                String command = scanner.nextLine();
                out.println(command);

                if ("stop".equals(command)) {
                    break;
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

class Listener extends Thread {

    BufferedReader in;

    Listener (BufferedReader in) {
        this.in = in;
    }

    public void run() {
        try {
            String msg;
            while ((msg = in.readLine()) != null) {
                System.out.println(msg);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
