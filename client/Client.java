import java.rmi.*;
import java.awt.FlowLayout;
import java.io.*;
import org.jdom2.*;
import org.jdom2.input.SAXBuilder;
import java.util.*;
import javax.swing.*;

public class Client {
    private static JFrame frame;
    private static JTextArea resultTextArea;

    public static void main(String args[]) {
        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
        });
    }

    private static void createAndShowGUI() {
        frame = new JFrame("RMI Client");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        JPanel choicePanel = new JPanel();
        choicePanel.setLayout(new FlowLayout());
        JLabel choiceLabel = new JLabel("Select an option:");
        String[] options = {
            "Add Student",
            "Delete Student",
            "Fetch Student",
            "Update Student",
            "Extract Data from XML",
            "Sort students"
        };
        JComboBox<String> choiceComboBox = new JComboBox<>(options);

        choicePanel.add(choiceLabel);
        choicePanel.add(choiceComboBox);

        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new FlowLayout());

        JButton actionButton = new JButton("Perform Action");

        actionButton.addActionListener(e -> {
            int choice = choiceComboBox.getSelectedIndex() + 1;
            performAction(choice);
        });
        actionPanel.add(actionButton);
        resultTextArea = new JTextArea(10, 30);
        resultTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultTextArea);
        contentPanel.add(choicePanel);
        contentPanel.add(actionPanel);
        contentPanel.add(scrollPane);
        frame.getContentPane().add(contentPanel);
        frame.setVisible(true);
    }

    private static void performAction(int choice) {
        String name = null, address = null, contact = null;
        int age = 0;

        try {
            //type cast sa StudentIntf
            StudentIntf stub = (StudentIntf) Naming.lookup("rmi://localhost:9100/student");

            switch (choice) {
                case 1:
                    name = JOptionPane.showInputDialog("Enter Name:");
                    age = Integer.parseInt(JOptionPane.showInputDialog("Enter Age:"));
                    address = JOptionPane.showInputDialog("Enter Address:");
                    contact = JOptionPane.showInputDialog("Enter Contact:");
                    stub.addStudent(name, age, address, contact);
                    setResultText("Student added successfully.");
                    break;

                case 2:
                    name = JOptionPane.showInputDialog("Enter Name of the student to delete:");
                    stub.deleteStudent(name);
                    setResultText("Student deleted successfully.");
                    break;

                case 3:
                    name = JOptionPane.showInputDialog("Enter Name of the student to fetch:");
                    String studInfo = stub.fetchStudent(name);
                    setResultText(studInfo);
                    break;

                case 4:
                    name = JOptionPane.showInputDialog("Enter Name of the student to update:");
                    age = Integer.parseInt(JOptionPane.showInputDialog("Enter New Age:"));
                    address = JOptionPane.showInputDialog("Enter New Address:");
                    contact = JOptionPane.showInputDialog("Enter New Contact:");
                    stub.updateStudent(name, age, address, contact);
                    break;

                case 5:
                    String xmlFilePath = JOptionPane.showInputDialog("Enter the path to the XML file:");
                    extractXML(xmlFilePath, stub);
                    setResultText("Students added successfully.");
                    break;

                case 6:
                    String sortField = JOptionPane.showInputDialog("Enter Sort Field:");
                    String sortedStudent = stub.sortStudent(sortField);
                    setResultText(sortedStudent);
                    break;

                default:
                    setResultText("Invalid choice.");
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void setResultText(String text) {
        resultTextArea.setText(text);
    }

    public static void extractXML(String xmlFilePath, StudentIntf stub) {
        try {
            SAXBuilder builder = new SAXBuilder();
            Document document = builder.build(new File(xmlFilePath));

            Element rootElement = document.getRootElement();
            List<Element> studentList = rootElement.getChildren("student");

            for (Element studentElement : studentList) {
                String name = studentElement.getChildText("name");
                int age = Integer.parseInt(studentElement.getChildText("age"));
                String address = studentElement.getChildText("address");
                String contact = studentElement.getChildText("contact");

                stub.addStudent(name, age, address, contact);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
