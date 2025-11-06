package StringApp;
import org.omg.CORBA.*;

public class StringImpl extends StringOpsPOA {
    public String reverse(String input) {
        return new StringBuilder(input).reverse().toString();
    }
    public String toUpper(String input) {
        return input.toUpperCase();
    }
}