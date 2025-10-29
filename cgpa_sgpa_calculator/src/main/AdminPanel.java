package main;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AdminPanel extends JFrame {

    private JTable table;
    private DefaultTableModel model;

    public AdminPanel() {
        setTitle("Admin Portal - Student Records");
        setSize(650, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // ---------- Table ----------
        String[] columns = {"ID", "Name", "SGPA", "CGPA"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // ---------- Buttons ----------
        JPanel btnPanel = new JPanel();
        JButton deleteBtn = new JButton("Delete");
        JButton refreshBtn = new JButton("Refresh");
        JButton backBtn = new JButton("Logout");

        btnPanel.add(deleteBtn);
        btnPanel.add(refreshBtn);
        btnPanel.add(backBtn);
        add(btnPanel, BorderLayout.SOUTH);

        // Load Data
        loadRecords();

        // ---------- Actions ----------
        deleteBtn.addActionListener(e -> deleteRecord());
        refreshBtn.addActionListener(e -> loadRecords());
        backBtn.addActionListener(e -> {
            dispose();
            new AdminLogin();
        });

        setVisible(true);
    }

    private void loadRecords() {
        model.setRowCount(0);
        List<DBConnection.StudentRecord> records = DBConnection.getAllRecords();

        if (records.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No student records found!");
            return;
        }

        for (DBConnection.StudentRecord r : records) {
            model.addRow(new Object[]{r.id, r.name, r.sgpa, r.cgpa});
        }
    }

    private void deleteRecord() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a record to delete!");
            return;
        }
        int id = (int) model.getValueAt(selectedRow, 0);
        DBConnection.deleteRecord(id);
        loadRecords();
        JOptionPane.showMessageDialog(this, "✅ Record deleted successfully!");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AdminPanel::new);
    }
}
