import java.rmi.Remote;
import java.rmi.RemoteException;

public interface StudentIntf extends Remote {
    public void addStudent(String name, int age, String address, String contact) throws RemoteException;
    public void updateStudent(String name, int age, String address, String contact) throws RemoteException;
    public String fetchStudent(String name) throws RemoteException;
    abstract String sortStudent(String sortField) throws RemoteException;
    abstract void deleteStudent(String name) throws RemoteException;
}
