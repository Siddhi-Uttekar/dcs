// import java.rmi.*;
// import java.rmi.registry.*;

// public class MyServer {
//     public static void main(String[] args) {
//         try {
//             Adder stub = new AdderRemote();
//              LocateRegistry.createRegistry(3000);
//             Naming.rebind("rmi://localhost:3000/Pooja", stub);
//             System.out.println("Server ready...");
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//     }
// }
import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.UnicastRemoteObject;

public class MyServer {
    public static void main(String[] args) {
        try {
            // ✅ Start RMI registry in the same JVM
            LocateRegistry.createRegistry(1099); // or 3000 if you prefer

            // Create remote object
            Adder stub = new AdderRemote(); // AdderRemote extends UnicastRemoteObject and implements Adder

            // Bind to registry
            Naming.rebind("rmi://localhost:1099/Pooja", stub);

            System.out.println("Server ready on port 1099...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
