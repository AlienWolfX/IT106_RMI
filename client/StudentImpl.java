import java.rmi.*;
import java.rmi.server.*;
import java.sql.*;

public class StudentImpl extends UnicastRemoteObject implements StudentIntf {

    StudentImpl() throws RemoteException {
        super();
    }
    public String fetchStudent(String name) throws RemoteException {
        String studentInfo = "";

        try {
            Connection rmiconn = null;
            Class.forName("com.mysql.cj.jdbc.Driver");
            rmiconn = DriverManager.getConnection("jdbc:mysql://localhost:3306/stud_info", "root", "");

            PreparedStatement pstmt = rmiconn.prepareStatement("SELECT * FROM `students` WHERE name = ?");
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int age = rs.getInt("age");
                String address = rs.getString("address");
                String contact = rs.getString("contact");
                studentInfo = "Name: " + name + ", Age: " + age + ", Address: " + address + ", Contact: " + contact;
            } else {
                studentInfo = "No student found with the name: " + name;
            }

            rs.close();
            pstmt.close();
            rmiconn.close();
        } catch (Exception e) {
            System.out.println("Not executed");
            System.out.println(e);
        }

        return studentInfo;
    }

    public void addStudent(String name, int age, String address, String contact) throws RemoteException {
        try {
            Connection rmiconn = null;
            Class.forName("com.mysql.cj.jdbc.Driver");
            rmiconn = DriverManager.getConnection("jdbc:mysql://localhost:3306/stud_info", "root", "");

            PreparedStatement pstmt = rmiconn.prepareStatement("INSERT INTO `students` (`name`, `age`, `address`, `contact`) VALUES (?, ?, ?, ?)");
            pstmt.setString(1, name);
            pstmt.setInt(2, age);
            pstmt.setString(3, address);
            pstmt.setString(4, contact);
            pstmt.executeUpdate();

            rmiconn.commit();

            pstmt.close();
            rmiconn.close();
        } catch (Exception e) {
            System.out.println("Not executed");
            System.out.println(e);
        }
    }

    public String sortStudent(String sortField) throws RemoteException {
        StringBuilder result = new StringBuilder();

        try {
            Connection rmiconn = null;
            Class.forName("com.mysql.cj.jdbc.Driver");
            rmiconn = DriverManager.getConnection("jdbc:mysql://localhost:3306/stud_info", "root", "");

            String query = "SELECT * FROM `students` ORDER BY " + sortField;
            PreparedStatement pstmt = rmiconn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                int age = rs.getInt("age");
                String address = rs.getString("address");
                String contact = rs.getString("contact");
                String studentInfo = "Name: " + name + ", Age: " + age + ", Address: " + address + ", Contact: " + contact;
                result.append(studentInfo).append("\n");
            }

            rs.close();
            pstmt.close();
            rmiconn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result.toString();
    }

    public void updateStudent(String name, int age, String address, String contact) throws RemoteException {
        try {
            Connection rmiconn = null;
            Class.forName("com.mysql.cj.jdbc.Driver");
            rmiconn = DriverManager.getConnection("jdbc:mysql://localhost:3306/stud_info", "root", "");

            PreparedStatement pstmt = rmiconn.prepareStatement("UPDATE `students` SET age = ?, address = ?, contact = ? WHERE name = ?");
            pstmt.setInt(1, age);
            pstmt.setString(2, address);
            pstmt.setString(3, contact);
            pstmt.setString(4, name);
            int rowsAffected = pstmt.executeUpdate();

            pstmt.close();
            rmiconn.close();

            System.out.println(rowsAffected + " student(s) updated in the database.");
        } catch (Exception e) {
            System.out.println("Error while updating student in the database:");
            e.printStackTrace();
        }
    }

    public void deleteStudent(String name) throws RemoteException {
        try {
            Connection rmiconn = null;
            Class.forName("com.mysql.cj.jdbc.Driver");
            rmiconn = DriverManager.getConnection("jdbc:mysql://localhost:3306/stud_info", "root", "");

            PreparedStatement pstmt = rmiconn.prepareStatement("DELETE FROM `students` WHERE name = ?");
            pstmt.setString(1, name);
            int rowsAffected = pstmt.executeUpdate();

            pstmt.close();
            rmiconn.close();

            System.out.println(rowsAffected + " student(s) deleted from the database.");
        } catch (Exception e) {
            System.out.println("Error while deleting student from the database:");
            e.printStackTrace();
        }
    }
}
