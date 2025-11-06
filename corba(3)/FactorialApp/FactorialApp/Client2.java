package FactorialApp;

import org.omg.CORBA.*;
import org.omg.CosNaming.*;

public class Client2 {
    public static void main(String args[]) {
        try {
            ORB orb = ORB.init(args, null);
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            Factorial fact = FactorialHelper.narrow(ncRef.resolve_str("FactorialService"));

            System.out.println("Factorial(3): " + fact.compute(3));
            System.out.println("Factorial(10): " + fact.compute(10));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}