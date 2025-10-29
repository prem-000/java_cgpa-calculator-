package main;

import javax.swing.*;
import java.awt.*;

public class AdminLogin extends JFrame {

    private JTextField emailField;

    public AdminLogin() {
        setTitle("Admin Login");
        setSize(350, 200);
        setLayout(new GridLayout(3, 1, 10, 10));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel title = new JLabel("Admin Login", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 16));

        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(new JLabel("Email: "));
        emailField = new JTextField(18);
        inputPanel.add(emailField);

        JButton loginBtn = new JButton("Login");
        loginBtn.addActionListener(e -> verifyLogin());

        add(title);
        add(inputPanel);
        add(loginBtn);

        setVisible(true);
    }

    private void verifyLogin() {
        String email = emailField.getText().trim();

        if (email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your email!");
            return;
        }

        if (email.endsWith("@fac.klu.ac.in")) {
            JOptionPane.showMessageDialog(this, "✅ Login successful!");
            new AdminPanel();
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "❌ Access denied! Only faculty emails allowed.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AdminLogin::new);
    }
}
