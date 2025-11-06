import java.rmi.*;
import java.rmi.server.*;

public class AdderRemote extends UnicastRemoteObject implements Adder {

    AdderRemote() throws RemoteException {
        super();
    }

    // Integer addition
    public int add(int x, int y, int clientPort) {
        int sum = x + y;
        try {
            String clientHost = RemoteServer.getClientHost();
            System.out.println("Client [" + clientHost + ":" + clientPort + "] requested: " + x + " + " + y);
            System.out.println("Sum is: " + sum);
        } catch (Exception e) {
            System.out.println("Could not fetch client host. Request: " + x + " + " + y + " = " + sum);
        }
        return sum;
    }

    // String concatenation
    public String concat(String s1, String s2, int clientPort) {
        String result = s1 + s2;
        try {
            String clientHost = RemoteServer.getClientHost();
            System.out.println("Client [" + clientHost + ":" + clientPort + "] requested CONCAT: \"" + s1 + "\" + \"" + s2 + "\"");
            System.out.println("Result: " + result);
        } catch (Exception e) {
            System.out.println("Concat: " + s1 + " + " + s2 + " = " + result);
        }
        return result;
    }

    // Convert to uppercase
    public String toUpper(String s, int clientPort) {
        String result = s.toUpperCase();
        try {
            String clientHost = RemoteServer.getClientHost();
            System.out.println("Client [" + clientHost + ":" + clientPort + "] requested TO UPPER: \"" + s + "\"");
            System.out.println("Result: " + result);
        } catch (Exception e) {
            System.out.println("Upper: " + s + " → " + result);
        }
        return result;
    }

    // Convert to lowercase
    public String toLower(String s, int clientPort) {
        String result = s.toLowerCase();
        try {
            String clientHost = RemoteServer.getClientHost();
            System.out.println("Client [" + clientHost + ":" + clientPort + "] requested TO LOWER: \"" + s + "\"");
            System.out.println("Result: " + result);
        } catch (Exception e) {
            System.out.println("Lower: " + s + " → " + result);
        }
        return result;
    }

    // Get string length
    public int getLength(String s, int clientPort) {
        int length = s.length();
        try {
            String clientHost = RemoteServer.getClientHost();
            System.out.println("Client [" + clientHost + ":" + clientPort + "] requested LENGTH of \"" + s + "\"");
            System.out.println("Length: " + length);
        } catch (Exception e) {
            System.out.println("Length of " + s + " = " + length);
        }
        return length;
    }
}
