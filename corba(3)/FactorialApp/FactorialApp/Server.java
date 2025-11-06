package FactorialApp;

import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.CosNaming.*;

public class Server {
    public static void main(String args[]) {
        try {
            ORB orb = ORB.init(args, null);
            POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootpoa.the_POAManager().activate();

            // Create and register Factorial service
            FactorialImpl factImpl = new FactorialImpl();
            org.omg.CORBA.Object ref = rootpoa.servant_to_reference(factImpl);
            Factorial factRef = FactorialHelper.narrow(ref);

            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            ncRef.rebind(ncRef.to_name("FactorialService"), factRef);

            System.out.println("✅ Factorial service registered successfully.");
            orb.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}