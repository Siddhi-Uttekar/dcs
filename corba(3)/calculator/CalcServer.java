// Import the package which contains the Server Skeleton
import WssCalculator.*;

// Import CORBA packages
import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;

// Server implementation
class CalcServerImpl extends CalcPOA {

    private ORB orb;

    public void setORB(ORB orb_val) {
        orb = orb_val;
    }

    // Implement calculate method
    public int calculate(int operator, int num1, int num2) {
        switch (operator) {
            case 1: return num1 + num2; // ADD
            case 2: return num1 - num2; // SUB
            case 3: return num1 * num2; // MUL
            case 4: 
                if (num2 != 0) return num1 / num2; // DIV
                else {
                    System.out.println("Division by zero error!");
                    return 0;
                }
            default: 
                System.out.println("Invalid operator!");
                return 0;
        }
    }

    // Shutdown method
    public void shutdown() {
        orb.shutdown(false);
    }
}

public class CalcServer {

    public static void main(String args[]) {
        try {
            // Create and initialize the ORB
            ORB orb = ORB.init(args, null);

            // Get reference to RootPOA & activate the POAManager
            POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootpoa.the_POAManager().activate();

            // Create servant and register it with ORB
            CalcServerImpl calcImpl = new CalcServerImpl();
            calcImpl.setORB(orb);

            // Get object reference from servant
            org.omg.CORBA.Object ref = rootpoa.servant_to_reference(calcImpl);
            Calc href = CalcHelper.narrow(ref);

            // Get naming context
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            // Bind the Object Reference in Naming
            String name = "Calc";
            NameComponent path[] = ncRef.to_name(name);
            ncRef.rebind(path, href);

            System.out.println("CalcServer ready and waiting ...");

            // Wait for invocations from clients
            orb.run();

        } catch (Exception e) {
            System.err.println("ERROR: " + e);
            e.printStackTrace(System.out);
        }

        System.out.println("CalcServer Exiting ...");
    }
}
