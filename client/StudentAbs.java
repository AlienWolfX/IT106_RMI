import java.rmi.RemoteException;

public abstract class StudentAbs {
    abstract void addStudent(String name, int age, String address, String contact) throws RemoteException;
    abstract void updateStudent(String name, int age, String address, String contact) throws RemoteException;
    abstract String fetchStudent(String name) throws RemoteException;
    abstract String sortStudent(String sortField) throws RemoteException;
    abstract void deleteStudent(String name) throws RemoteException;
}