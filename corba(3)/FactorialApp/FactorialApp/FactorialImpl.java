package FactorialApp;
import org.omg.CORBA.*;

public class FactorialImpl extends FactorialPOA {
    public int compute(int n) {
        if (n <= 1) return 1;
        return n * compute(n - 1);
    }
}

/* NOTE:
If after running idlj, the generated file FactorialOperations.java shows:
  int compute(int n);
then change your implementation to use int instead of long:

package FactorialApp;
import org.omg.CORBA.*;
public class FactorialImpl extends FactorialPOA {
    public int compute(int n) {
        if (n <= 1) return 1;
        return n * compute(n - 1);
    }
}
*/