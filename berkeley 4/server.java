import java.io.*;
import java.net.*;
import java.util.*;

public class server {
    public static void main(String[] args) {
        int port = 8888;
        int clientCount = 3;
        List<Socket> clients = new ArrayList<>();

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started on port " + port);
            System.out.println("Waiting for " + clientCount + " clients to connect...");

            // Accept clients
            while (clients.size() < clientCount) {
                Socket clientSocket = serverSocket.accept();
                clients.add(clientSocket);
                System.out.println("Client " + clients.size() + " connected.");
            }

            // Send server time to all clients
            long serverTime = System.currentTimeMillis();
            System.out.println("Server time to send: " + new Date(serverTime));

            for (Socket client : clients) {
                PrintWriter out = new PrintWriter(client.getOutputStream(), true);
                out.println(serverTime);  // send time as string
                out.flush();
                // Give client time to read before closing
                Thread.sleep(500);
                client.close();
            }

            System.out.println("Time sent to all clients. Server exiting.");

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
