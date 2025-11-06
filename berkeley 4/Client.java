 import java.io.*;
import java.net.*;
import java.util.*;

public class Client {
   public static void main(String[] args) {
       String serverAddress = "localhost"; // use server IP if remote
       int port = 8888;

       try (Socket socket = new Socket(serverAddress, port);
            BufferedReader in = new BufferedReader(new
InputStreamReader(socket.getInputStream()))) {

           System.out.println("Connected to server.");

           // Read server time exactly once
           String timeString = in.readLine();
           if(timeString != null) {
               long serverTime = Long.parseLong(timeString);
               System.out.println("Local time before sync: " + new
Date());
               System.out.println("Server time received: " + new
Date(serverTime));
           } else {
               System.out.println("Received null from server!");
           }

           socket.close();  // safely close socket

       } catch (IOException e) {
           e.printStackTrace();
       }
   }
}
