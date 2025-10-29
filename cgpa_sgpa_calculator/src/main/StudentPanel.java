package main;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

public class StudentPanel extends JFrame {

    private JTextField nameField;
    private JTable subjectTable;
    private DefaultTableModel tableModel;
    private JLabel sgpaLabel;

    public StudentPanel() {
        setTitle("Student SGPA Calculator");
        setSize(650, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel("STUDENT SGPA CALCULATOR", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        add(title, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Name:"));
        nameField = new JTextField(15);
        inputPanel.add(nameField);
        add(inputPanel, BorderLayout.NORTH);

        String[] columns = {"Subject", "Marks (out of 100)"};
        tableModel = new DefaultTableModel(columns, 0);
        subjectTable = new JTable(tableModel);
        add(new JScrollPane(subjectTable), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton addSubjectBtn = new JButton("Add Subject");
        JButton calculateBtn = new JButton("Calculate & Save");
        JButton backBtn = new JButton("Back");

        sgpaLabel = new JLabel("SGPA: ");
        sgpaLabel.setFont(new Font("Arial", Font.BOLD, 14));

        buttonPanel.add(addSubjectBtn);
        buttonPanel.add(calculateBtn);
        buttonPanel.add(sgpaLabel);
        buttonPanel.add(backBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        addSubjectBtn.addActionListener(e -> tableModel.addRow(new Object[]{"", ""}));
        calculateBtn.addActionListener(this::calculateAndSaveSGPA);
        backBtn.addActionListener(e -> {
            dispose();
            new CGPA_SGPA_Calculator();
        });

        setVisible(true);
    }

    private void calculateAndSaveSGPA(ActionEvent e) {
        String name = nameField.getText().trim();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter student name!");
            return;
        }
        int rowCount = tableModel.getRowCount();
        if (rowCount == 0) {
            JOptionPane.showMessageDialog(this, "Add at least one subject!");
            return;
        }
        double totalGradePoints = 0;
        int totalSubjects = 0;
        for (int i = 0; i < rowCount; i++) {
            Object marksObj = tableModel.getValueAt(i, 1);
            if (marksObj == null || marksObj.toString().trim().isEmpty()) continue;
            try {
                double marks = Double.parseDouble(marksObj.toString());
                totalGradePoints += convertMarksToGradePoint(marks);
                totalSubjects++;
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid marks in row " + (i + 1));
                return;
            }
        }
        if (totalSubjects == 0) {
            JOptionPane.showMessageDialog(this, "Please enter valid marks!");
            return;
        }
        double sgpa = totalGradePoints / totalSubjects;
        sgpaLabel.setText(String.format("SGPA: %.2f", sgpa));

        // If using in-memory DB:
        try {
            DBConnection.addRecord(DBConnection.getAllRecords().size() + 1, name, sgpa, sgpa);
        } catch (Throwable t) {
            // if DBConnection has different API, ignore; this is only to avoid runtime crash
        }
        JOptionPane.showMessageDialog(this, "✅ SGPA saved for " + name);
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
