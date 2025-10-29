package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class AdminPanel extends JFrame {
    private JTextArea displayArea;
    private JTextField emailField, idField, nameField, cgpaField, sgpaField;
    private JButton addBtn, updateBtn, deleteBtn, refreshBtn;

    public AdminPanel() {
        setTitle("Admin Panel - Faculty Access");
        setSize(650, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // ---------- Email Verification ----------
        JPanel emailPanel = new JPanel(new FlowLayout());
        emailPanel.add(new JLabel("Faculty Email:"));
        emailField = new JTextField(25);
        emailPanel.add(emailField);
        JButton loginBtn = new JButton("Login");
        emailPanel.add(loginBtn);
        add(emailPanel, BorderLayout.NORTH);

        // ---------- Display Area ----------
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(displayArea);
        add(scrollPane, BorderLayout.CENTER);

        // ---------- Input Panel ----------
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 8, 8));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Manage Records"));

        idField = new JTextField();
        nameField = new JTextField();
        cgpaField = new JTextField();
        sgpaField = new JTextField();

        inputPanel.add(new JLabel("Reg No:"));
        inputPanel.add(idField);
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("CGPA:"));
        inputPanel.add(cgpaField);
        inputPanel.add(new JLabel("SGPA:"));
        inputPanel.add(sgpaField);

        // ---------- Button Panel ----------
        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 10, 10));
        addBtn = new JButton("Add");
        updateBtn = new JButton("Update");
        deleteBtn = new JButton("Delete");
        refreshBtn = new JButton("Refresh");

        buttonPanel.add(addBtn);
        buttonPanel.add(updateBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(refreshBtn);

        // Bottom wrapper
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(inputPanel, BorderLayout.CENTER);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(bottomPanel, BorderLayout.SOUTH);

        // ---------- Initial Disable Buttons ----------
        toggleControls(false);

        // ---------- Button Actions ----------
        loginBtn.addActionListener(this::handleLogin);
        addBtn.addActionListener(this::addRecord);
        updateBtn.addActionListener(this::updateRecord);
        deleteBtn.addActionListener(this::deleteRecord);
        refreshBtn.addActionListener(_ -> refreshRecords());

        setVisible(true);
    }

    // ---------- Login Verification ----------
    private void handleLogin(ActionEvent e) {
        String email = emailField.getText().trim();

        if (email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your faculty email!");
            return;
        }

        if (!email.endsWith("@faculty.klu.ac.in")) {
            JOptionPane.showMessageDialog(this,
                    "Access Denied! Only KLU faculty emails are allowed.",
                    "Invalid Access", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Valid login
        JOptionPane.showMessageDialog(this, "✅ Access granted!");
        toggleControls(true);
        refreshRecords();
    }

    // ---------- Add Record ----------
    private void addRecord(ActionEvent e) {
        try {
            int id = Integer.parseInt(idField.getText().trim());
            String name = nameField.getText().trim();
            double cgpa = Double.parseDouble(cgpaField.getText().trim());
            double sgpa = Double.parseDouble(sgpaField.getText().trim());

            DBConnection.addRecord(id, name, cgpa, sgpa);
            JOptionPane.showMessageDialog(this, "Record added successfully!");
            refreshRecords();
            clearFields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "⚠️ Invalid input! Please check the details.");
        }
    }

    // ---------- Update Record ----------
    private void updateRecord(ActionEvent e) {
        try {
            int id = Integer.parseInt(idField.getText().trim());
            String name = nameField.getText().trim();
            double cgpa = Double.parseDouble(cgpaField.getText().trim());
            double sgpa = Double.parseDouble(sgpaField.getText().trim());

            DBConnection.updateRecord(id, name, cgpa, sgpa);
            JOptionPane.showMessageDialog(this, "Record updated successfully!");
            refreshRecords();
            clearFields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "⚠️ Invalid input or ID not found.");
        }
    }

    // ---------- Delete Record ----------
    private void deleteRecord(ActionEvent e) {
        try {
            int id = Integer.parseInt(idField.getText().trim());
            DBConnection.deleteRecord(id);
            JOptionPane.showMessageDialog(this, "Record deleted successfully!");
            refreshRecords();
            clearFields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "⚠️ Invalid ID.");
        }
    }

    // ---------- Refresh ----------
    private void refreshRecords() {
        displayArea.setText(DBConnection.getAllRecords());
    }

    // ---------- Helper Methods ----------
    private void toggleControls(boolean enable) {
        idField.setEnabled(enable);
        nameField.setEnabled(enable);
        cgpaField.setEnabled(enable);
        sgpaField.setEnabled(enable);
        addBtn.setEnabled(enable);
        updateBtn.setEnabled(enable);
        deleteBtn.setEnabled(enable);
        refreshBtn.setEnabled(enable);
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        cgpaField.setText("");
        sgpaField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AdminPanel::new);
    }
}
