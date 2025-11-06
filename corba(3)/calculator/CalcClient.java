// Import all the important packages

// Import the package which contains the Client Stub
import WssCalculator.*;

// Import the below two packages to use the Naming Service
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;

// Import this package to run the CORBA Application
import org.omg.CORBA.*;

// Import to perform Input-Output functionalities
import java.io.*;

public class CalcClient {

    static Calc cimpl;

    public static void main(String args[]) {

        try {
            // Create and Initialize the ORB object
            ORB orb = ORB.init(args, null);

            // Obtain the initial Naming Context
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");

            // Narrow the objref to its proper type
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            // Identify a String to refer the Naming Service to Calc object
            String name = "Calc";

            // Get a reference to the CalcServer and Narrow it to Calc object
            cimpl = CalcHelper.narrow(ncRef.resolve_str(name));

            // Initial header
            System.out.println("==========================================");
            System.out.println("   CORBA Calculator Application");
            System.out.println("==========================================\n");

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            int x = 1;
            while (x == 1) {
                // Instructions
                System.out.println("Enter operation in format: <operator> <num1> <num2>");
                System.out.println("----------------------------------------------------");
                System.out.println("Operators:");
                System.out.println("   1 = ADD");
                System.out.println("   2 = SUB");
                System.out.println("   3 = MUL");
                System.out.println("   4 = DIV\n");
                System.out.println("----------------------------------------------------");
                System.out.print("Enter your input: ");

                String abc = br.readLine();

                // Split input into parts
                String[] parts = abc.trim().split("\\s+");
                if (parts.length != 3) {
                    System.out.println("\n⚠ Invalid input! Use: <operator> <num1> <num2>");
                    System.out.println("----------------------------------------------------\n");
                    continue;
                }

                int i = Integer.parseInt(parts[0]); // operator code
                int j = Integer.parseInt(parts[1]); // first number
                int k = Integer.parseInt(parts[2]); // second number

                int result = cimpl.calculate(i, j, k);

                // Display result
                System.out.println("\nResult of the operation: " + result);
                System.out.println("----------------------------------------------------");

                // Continue/exit prompt
                System.out.print("Do you want to continue? (1 = Yes / 0 = No): ");
                x = Integer.parseInt(br.readLine());
                System.out.println();
            }

            // If the Client wants to discontinue
            System.out.println(">> Shutting down server...");
            cimpl.shutdown();
            System.out.println(">> Client terminated successfully.");
            System.out.println("==========================================");

        } catch (Exception e) {
            System.out.println("ERROR: " + e);
            e.printStackTrace(System.out);
        }
    }
}
