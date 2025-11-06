import java.rmi.*;

public interface Adder extends Remote {
    int add(int x, int y, int clientPort) throws RemoteException;

    // String operations
    String concat(String s1, String s2, int clientPort) throws RemoteException;
    String toUpper(String s, int clientPort) throws RemoteException;
    String toLower(String s, int clientPort) throws RemoteException;
    int getLength(String s, int clientPort) throws RemoteException;
}
