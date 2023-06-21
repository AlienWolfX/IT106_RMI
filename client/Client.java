import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class Client extends JFrame implements ActionListener {
    private DefaultListModel<String> studentListModel;
    private JList<String> studentList;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Client clientGUI = new Client();
            clientGUI.createAndShowGUI();
        });
    }

    private void createAndShowGUI() {
        setTitle("Student Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(500, 300);

        // Initialize the student list model and JList
        studentListModel = new DefaultListModel<>();
        studentList = new JList<>(studentListModel);
        JScrollPane scrollPane = new JScrollPane(studentList);

        // Create buttons
        JButton sortButton = new JButton("Sort");
        JButton displayButton = new JButton("Display");
        JButton deleteButton = new JButton("Delete");
        JButton updateButton = new JButton("Update");
        JButton extractButton = new JButton("Extract XML");

        // Register event listeners for the buttons
        sortButton.addActionListener(this);
        displayButton.addActionListener(this);
        deleteButton.addActionListener(this);
        updateButton.addActionListener(this);
        extractButton.addActionListener(this);

        // Create a panel to hold the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(sortButton);
        buttonPanel.add(displayButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(extractButton);

        // Add components to the frame
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Sort":
                sortStudents();
                break;
            case "Display":
                displayStudents();
                break;
            case "Delete":
                deleteStudent();
                break;
            case "Update":
                updateStudent();
                break;
            case "Extract XML":
                extractXML();
                break;
        }
    }

    private void sortStudents() {
    try {
        // Parse XML to extract student information
        File xmlFile = new File("C:\\Users\\User\\Downloads\\RMI_JDOM\\RMI\\client\\xml\\stud1.xml");
        SAXBuilder saxBuilder = new SAXBuilder();
        Document document = saxBuilder.build(xmlFile);

        Element rootElement = document.getRootElement();
        List<Element> studentElements = rootElement.getChildren("student");

        // Sort the student elements based on a specific criteria (e.g., name)
        Collections.sort(studentElements, new Comparator<Element>() {
            @Override
            public int compare(Element e1, Element e2) {
                String name1 = e1.getChildText("name");
                String name2 = e2.getChildText("name");
                return name1.compareTo(name2);
            }
        });

        // Clear the studentListModel and populate it with sorted student data
        studentListModel.clear();
        for (Element studentElement : studentElements) {
            String studentName = studentElement.getChildText("name");
            String studAge = studentElement.getChildText("age");
            String studAddress = studentElement.getChildText("address");
            String studContact = studentElement.getChildText("contact");

            String studentData = "Name: " + studentName + ", Age: " + studAge + ", Address: " + studAddress + ", Contact: " + studContact;
            studentListModel.addElement(studentData);
        }
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}

    private void displayStudents() {
        try {
            // Parse XML to extract student information
            File xmlFile = new File("C:\\Users\\User\\Downloads\\RMI_JDOM\\RMI\\client\\xml\\stud1.xml");
            SAXBuilder saxBuilder = new SAXBuilder();
            Document document = saxBuilder.build(xmlFile);

            Element rootElement = document.getRootElement();
            List<Element> studentElements = rootElement.getChildren("student");

            // Clear the studentListModel and populate it with student data
            studentListModel.clear();
            for (Element studentElement : studentElements) {
                String studentName = studentElement.getChildText("name");
                String studAge = studentElement.getChildText("age");
                String studAddress = studentElement.getChildText("address");
                String studContact = studentElement.getChildText("contact");

                String studentData = "Name: " + studentName + ", Age: " + studAge + ", Address: " + studAddress + ", Contact: " + studContact;
                studentListModel.addElement(studentData);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteStudent() {
    try {
        // Parse XML to extract student information
        File xmlFile = new File("C:\\Users\\User\\Downloads\\RMI_JDOM\\RMI\\client\\xml\\stud1.xml");
        SAXBuilder saxBuilder = new SAXBuilder();
        Document document = saxBuilder.build(xmlFile);

        Element rootElement = document.getRootElement();
        List<Element> studentElements = rootElement.getChildren("student");

        // Display a dialog to select the student to delete
        String[] studentNames = new String[studentElements.size()];
        for (int i = 0; i < studentElements.size(); i++) {
            Element studentElement = studentElements.get(i);
            String studentName = studentElement.getChildText("name");
            studentNames[i] = studentName;
        }
        String selectedStudent = (String) JOptionPane.showInputDialog(
                this,
                "Select a student to delete:",
                "Delete Student",
                JOptionPane.PLAIN_MESSAGE,
                null,
                studentNames,
                studentNames[0]);

        // Find the selected student and remove it from the XML
        for (Element studentElement : studentElements) {
            String studentName = studentElement.getChildText("name");
            if (studentName.equals(selectedStudent)) {
                studentElement.detach(); // Remove the student element from the XML
                break;
            }
        }

        // Update the XML file
        XMLOutputter xmlOutputter = new XMLOutputter();
        xmlOutputter.setFormat(Format.getPrettyFormat());
        xmlOutputter.output(document, new FileWriter(xmlFile));

        // Update the studentListModel by displaying the remaining students
        displayStudents();

        JOptionPane.showMessageDialog(this, "Student deleted successfully.", "Delete Student", JOptionPane.INFORMATION_MESSAGE);
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}


    private void updateStudent() {
    try {
        // Parse XML to extract student information
        File xmlFile = new File("C:\\Users\\User\\Downloads\\RMI_JDOM\\RMI\\client\\xml\\stud1.xml");
        SAXBuilder saxBuilder = new SAXBuilder();
        Document document = saxBuilder.build(xmlFile);

        Element rootElement = document.getRootElement();
        List<Element> studentElements = rootElement.getChildren("student");

        // Retrieve the selected student from the XML
        int selectedIndex = studentList.getSelectedIndex();
        if (selectedIndex >= 0 && selectedIndex < studentElements.size()) {
            Element selectedStudent = studentElements.get(selectedIndex);

            // Prompt the user to enter updated student information
            String updatedName = JOptionPane.showInputDialog(this, "Enter updated name:");
            String updatedAge = JOptionPane.showInputDialog(this, "Enter updated age:");
            String updatedAddress = JOptionPane.showInputDialog(this, "Enter updated address:");
            String updatedContact = JOptionPane.showInputDialog(this, "Enter updated contact:");

            // Update the student element with the new information
            selectedStudent.getChild("name").setText(updatedName);
            selectedStudent.getChild("age").setText(updatedAge);
            selectedStudent.getChild("address").setText(updatedAddress);
            selectedStudent.getChild("contact").setText(updatedContact);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a student to update.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Update the XML file
        XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
        xmlOutputter.output(document, new FileWriter(xmlFile));

        // Update the studentListModel by re-populating it with the updated student data
        studentListModel.clear();
        for (Element studentElement : studentElements) {
            String studentName = studentElement.getChildText("name");
            String studAge = studentElement.getChildText("age");
            String studAddress = studentElement.getChildText("address");
            String studContact = studentElement.getChildText("contact");

            String studentData = "Name: " + studentName + ", Age: " + studAge + ", Address: " + studAddress + ", Contact: " + studContact;
            studentListModel.addElement(studentData);
        }
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}


    private void extractXML() {
    try {
        // Parse XML to extract student information
        File xmlFile = new File("C:\\Users\\User\\Downloads\\RMI_JDOM\\RMI\\client\\xml\\stud1.xml");
        SAXBuilder saxBuilder = new SAXBuilder();
        Document document = saxBuilder.build(xmlFile);

        Element rootElement = document.getRootElement();
        List<Element> studentElements = rootElement.getChildren("student");

        // Clear the studentListModel and populate it with student data
        studentListModel.clear();
        for (Element studentElement : studentElements) {
            String studentName = studentElement.getChildText("name");
            String studAge = studentElement.getChildText("age");
            String studAddress = studentElement.getChildText("address");
            String studContact = studentElement.getChildText("contact");

            String studentData = "Name: " + studentName + ", Age: " + studAge + ", Address: " + studAddress + ", Contact: " + studContact;
            studentListModel.addElement(studentData);
        }
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}

}
