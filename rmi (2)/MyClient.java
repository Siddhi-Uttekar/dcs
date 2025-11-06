import java.rmi.*;
import java.util.Scanner;
import java.net.*;

public class MyClient {
    public static void main(String[] args) {
        try {
            Adder stub = (Adder) Naming.lookup("rmi://localhost:3000/Pooja");
            Scanner sc = new Scanner(System.in);

            // Temporary socket to identify the client
            Socket tempSocket = new Socket("localhost", 3000);
            int clientPort = tempSocket.getLocalPort();
            tempSocket.close();

            int choice;

            do {
                System.out.println("\n========= RMI String & Math Operations =========");
                System.out.println("1. Add two numbers");
                System.out.println("2. Concatenate two strings");
                System.out.println("3. Convert string to UPPERCASE");
                System.out.println("4. Convert string to lowercase");
                System.out.println("5. Get string length");
                System.out.println("6. Exit");
                System.out.print("Enter your choice: ");
                choice = sc.nextInt();
                sc.nextLine(); // consume newline

                switch (choice) {
                    case 1:
                        System.out.print("Enter first number: ");
                        int a = sc.nextInt();
                        System.out.print("Enter second number: ");
                        int b = sc.nextInt();
                        int sum = stub.add(a, b, clientPort);
                        System.out.println("Sum from server: " + sum);
                        break;

                    case 2:
                        System.out.print("Enter first string: ");
                        String s1 = sc.nextLine();
                        System.out.print("Enter second string: ");
                        String s2 = sc.nextLine();
                        String concat = stub.concat(s1, s2, clientPort);
                        System.out.println("Concatenated result: " + concat);
                        break;

                    case 3:
                        System.out.print("Enter string: ");
                        String upperInput = sc.nextLine();
                        String upper = stub.toUpper(upperInput, clientPort);
                        System.out.println("Uppercase result: " + upper);
                        break;

                    case 4:
                        System.out.print("Enter string: ");
                        String lowerInput = sc.nextLine();
                        String lower = stub.toLower(lowerInput, clientPort);
                        System.out.println("Lowercase result: " + lower);
                        break;

                    case 5:
                        System.out.print("Enter string: ");
                        String lenInput = sc.nextLine();
                        int len = stub.getLength(lenInput, clientPort);
                        System.out.println("Length: " + len);
                        break;

                    case 6:
                        System.out.println("Exiting client...");
                        break;

                    default:
                        System.out.println("Invalid choice! Try again.");
                }

            } while (choice != 6);

            sc.close();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
}
