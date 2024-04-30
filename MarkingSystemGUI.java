import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class MarkingSystemGUI extends JFrame {
    private JLabel nameLabel;
    private JTextField nameField;
    private JLabel[] labels;
    private JTextField[] textFields;
    private JButton calculateButton;
    private JLabel resultLabel;

    public MarkingSystemGUI() {
        setTitle("Marking System");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(9, 2));

        // Create label and text field for student's name
        nameLabel = new JLabel("Student Name: ");
        nameField = new JTextField();
        add(nameLabel);
        add(nameField);

        // Create labels and text fields for subjects
        labels = new JLabel[7];
        textFields = new JTextField[7];
        String[] subjects = {"Account", "Economics", "BO", "SPCC", "State Computer", "Gujarati", "English"};
        for (int i = 0; i < 7; i++) {
            labels[i] = new JLabel(subjects[i] + ": ");
            textFields[i] = new JTextField();
            add(labels[i]);
            add(textFields[i]);
        }

        // Create calculate button
        calculateButton = new JButton("Calculate");
        add(calculateButton);
        calculateButton.addActionListener(new CalculateButtonListener());

        // Create result label
        resultLabel = new JLabel("");
        add(resultLabel);

        setVisible(true);
    }

    private class CalculateButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String studentName = nameField.getText();

            // Calculate total marks
            double totalMarks = 0;
            boolean fail = false;
            for (int i = 0; i < 7; i++) {
                try {
                    double marks = Double.parseDouble(textFields[i].getText());
                    if (marks < 33) {
                        fail = true;
                    }
                    totalMarks += marks;
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter valid marks for all subjects.");
                    return;
                }
            }

            // Calculate percentage
            double percentage = (totalMarks / 7);

            // Display result
            if (fail) {
                resultLabel.setText("<html>Student: " + studentName + "<br>Percentage: <font color='red'>" + String.format("%.2f", percentage) + "% Fail</font></html>");
            } else {
                resultLabel.setText("<html>Student: " + studentName + "<br>Percentage: <font color='green'>" + String.format("%.2f", percentage) + "% Pass</font></html>");
            }

            // Write student's information to file
            writeToFile(studentName, totalMarks, percentage);
        }

        private void writeToFile(String studentName, double totalMarks, double percentage) {
            try {
                File file = new File("marks.txt");
                FileWriter writer = new FileWriter(file, true);
                BufferedWriter bufferedWriter = new BufferedWriter(writer);

                bufferedWriter.write("Student: " + studentName + ", Total Marks: " + totalMarks + ", Percentage: " + String.format("%.2f", percentage) + "%");
                bufferedWriter.newLine();

                bufferedWriter.close();
                writer.close();

                System.out.println("Data saved to marks.txt");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MarkingSystemGUI();
            }
        });
    }
}
