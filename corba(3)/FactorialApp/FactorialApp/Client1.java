package FactorialApp;

import org.omg.CORBA.*;
import org.omg.CosNaming.*;

public class Client1 {
    public static void main(String args[]) {
        try {
            ORB orb = ORB.init(args, null);
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            Factorial fact = FactorialHelper.narrow(ncRef.resolve_str("FactorialService"));

            System.out.println("Factorial(5): " + fact.compute(5));
            System.out.println("Factorial(7): " + fact.compute(7));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}