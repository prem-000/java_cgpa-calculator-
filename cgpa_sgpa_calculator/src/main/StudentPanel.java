package main;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

public class StudentPanel extends JFrame {

    private JTextField nameField, regField;
    private JTable subjectTable;
    private DefaultTableModel tableModel;
    private JLabel sgpaLabel;

    public StudentPanel() {
        setTitle("Student SGPA Calculator");
        setSize(650, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // ---------- Header ----------
        JLabel title = new JLabel("SGPA CALCULATOR", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        add(title, BorderLayout.NORTH);

        // ---------- Input Panel ----------
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        inputPanel.add(new JLabel("Reg. No:"));
        regField = new JTextField(8);
        inputPanel.add(regField);

        inputPanel.add(new JLabel("Name:"));
        nameField = new JTextField(15);
        inputPanel.add(nameField);

        add(inputPanel, BorderLayout.NORTH);

        // ---------- Table ----------
        String[] columns = {"Subject Name", "Marks (out of 100)"};
        tableModel = new DefaultTableModel(columns, 0);
        subjectTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(subjectTable);
        add(scrollPane, BorderLayout.CENTER);

        // ---------- Buttons Panel ----------
        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton addSubjectBtn = new JButton("Add Subject");
        JButton calculateBtn = new JButton("Calculate & Save SGPA");
        JButton backBtn = new JButton("Back");

        sgpaLabel = new JLabel("SGPA: ");
        sgpaLabel.setFont(new Font("Arial", Font.BOLD, 14));

        buttonPanel.add(addSubjectBtn);
        buttonPanel.add(calculateBtn);
        buttonPanel.add(sgpaLabel);
        buttonPanel.add(backBtn);

        add(buttonPanel, BorderLayout.SOUTH);

        // ---------- Button Actions ----------

        // Add new subject row
        addSubjectBtn.addActionListener(e -> tableModel.addRow(new Object[]{"", ""}));

        // Calculate SGPA and Save to DB
        calculateBtn.addActionListener(this::calculateAndSaveSGPA);

        // Back button
        backBtn.addActionListener(e -> {
            new CGPA_SGPA_Calculator();
            dispose();
        });

        setVisible(true);
    }

    private void calculateAndSaveSGPA(ActionEvent e) {
        int rowCount = tableModel.getRowCount();
        if (rowCount == 0) {
            JOptionPane.showMessageDialog(this, "Please add at least one subject!");
            return;
        }

        String name = nameField.getText().trim();
        String regStr = regField.getText().trim();

        if (name.isEmpty() || regStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter registration number and name!");
            return;
        }

        double totalGradePoints = 0;
        double totalSubjects = 0;

        try {
            for (int i = 0; i < rowCount; i++) {
                String marksStr = tableModel.getValueAt(i, 1).toString();
                if (marksStr.isEmpty()) continue;

                double marks = Double.parseDouble(marksStr);
                double gradePoint = convertMarksToGradePoint(marks);
                totalGradePoints += gradePoint;
                totalSubjects++;
            }

            if (totalSubjects == 0) {
                JOptionPane.showMessageDialog(this, "Please enter marks for subjects!");
                return;
            }

            double sgpa = totalGradePoints / totalSubjects;
            sgpaLabel.setText(String.format("SGPA: %.2f", sgpa));

            // ---------- Save to Database ----------
            int regNo = Integer.parseInt(regStr);
            DBConnection.addRecord(regNo, name, 0.0, sgpa); // CGPA = 0 for now

            JOptionPane.showMessageDialog(this, "✅ SGPA saved successfully for " + name);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numeric values for marks and Reg. No!");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "❌ Error saving data to database!");
        }
    }

    private double convertMarksToGradePoint(double marks) {
        if (marks >= 90) return 10;
        else if (marks >= 80) return 9;
        else if (marks >= 70) return 8;
        else if (marks >= 60) return 7;
        else if (marks >= 50) return 6;
        else if (marks >= 40) return 5;
        else return 0;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StudentPanel::new);
    }
}
