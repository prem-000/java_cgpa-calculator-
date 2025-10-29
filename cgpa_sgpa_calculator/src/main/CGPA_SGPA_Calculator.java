package main;

import javax.swing.*;
import java.awt.*;

public class CGPA_SGPA_Calculator extends JFrame {

    public CGPA_SGPA_Calculator() {
        setTitle("CGPA & SGPA Calculator");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 1, 10, 10));

        JLabel title = new JLabel("Choose User Type", SwingConstants.CENTER);
        JButton adminBtn = new JButton("Admin");
        JButton studentBtn = new JButton("Student");

        add(title);
        add(adminBtn);
        add(studentBtn);

        adminBtn.addActionListener(e -> {
            new AdminPanel();
            dispose();
        });

        studentBtn.addActionListener(e -> {
            new StudentPanel();
            dispose();
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CGPA_SGPA_Calculator::new);
    }
}
