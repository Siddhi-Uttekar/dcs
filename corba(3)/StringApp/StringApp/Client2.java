package StringApp;

import org.omg.CORBA.*;
import org.omg.CosNaming.*;

public class Client2 {
    public static void main(String args[]) {
        try {
            ORB orb = ORB.init(args, null);
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            StringOps str = StringOpsHelper.narrow(ncRef.resolve_str("StringService"));

            System.out.println("Reverse: " + str.reverse("CORBA"));
            System.out.println("Upper: " + str.toUpper("middleware"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}