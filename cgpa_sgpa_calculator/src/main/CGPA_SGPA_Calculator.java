package main;

import javax.swing.*;
import java.awt.*;

public class CGPA_SGPA_Calculator extends JFrame {

    public CGPA_SGPA_Calculator() {
        setTitle("CGPA & SGPA Calculator");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 1, 10, 10));

        JLabel title = new JLabel("Select Your Role", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        add(title);

        JButton studentBtn = new JButton("Student Panel");
        JButton adminBtn = new JButton("Admin Panel");

        studentBtn.setFont(new Font("Arial", Font.BOLD, 14));
        adminBtn.setFont(new Font("Arial", Font.BOLD, 14));

        // ---------- Student Button ----------
        studentBtn.addActionListener(e -> {
            dispose();
            new StudentPanel();
        });

        // ---------- Admin Button ----------
        adminBtn.addActionListener(e -> {
            // Simple login dialog
            JTextField emailField = new JTextField();
            JPasswordField passField = new JPasswordField();
            Object[] fields = {
                "Email:", emailField,
                "Password:", passField
            };

            int option = JOptionPane.showConfirmDialog(
                null, fields, "Admin Login", JOptionPane.OK_CANCEL_OPTION
            );

            if (option == JOptionPane.OK_OPTION) {
                String email = emailField.getText().trim();
                String password = new String(passField.getPassword()).trim();

                // Check admin credentials
                if (email.endsWith("@fac.klu.ac.in") && !password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "✅ Login successful!");
                    dispose();
                    new AdminPanel();
                } else {
                    JOptionPane.showMessageDialog(null,
                        "❌ Invalid credentials! Use a faculty email (@fac.klu.ac.in).",
                        "Access Denied", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        add(studentBtn);
        add(adminBtn);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CGPA_SGPA_Calculator::new);
    }
}
