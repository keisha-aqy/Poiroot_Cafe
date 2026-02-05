// EndingForm.java
package view;

import javax.swing.*;
import java.awt.*;

public class EndingForm extends JFrame {
    public EndingForm() {
        setTitle("Poirot Cafe - Terima Kasih");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        initUI();
    }

    private void initUI() {
        // Main panel with gradient background
        JPanel mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = new Color(139, 90, 43); // Brown
                Color color2 = new Color(101, 67, 33); // Dark Brown
                GradientPaint gp = new GradientPaint(0, 0, color1, getWidth(), getHeight(), color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        // Center panel
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 20, 20, 20);

        // Cafe logo/name
        JLabel cafeName = new JLabel("Poirot Cafe");
        cafeName.setFont(new Font("Brush Script MT", Font.BOLD, 72));
        cafeName.setForeground(new Color(255, 215, 0));
        centerPanel.add(cafeName, gbc);

        // Success message
        gbc.gridy = 1;
        JLabel message = new JLabel("Your order is being prepared, please wait");
        message.setFont(new Font("Segoe UI", Font.BOLD, 24));
        message.setForeground(Color.WHITE);
        centerPanel.add(message, gbc);

        // Animated dots
        gbc.gridy = 2;
        JLabel dots = new JLabel(". . .");
        dots.setFont(new Font("Segoe UI", Font.BOLD, 36));
        dots.setForeground(Color.WHITE);
        centerPanel.add(dots, gbc);

        // Timer for dots animation
        Timer timer = new Timer(500, e -> {
            String text = dots.getText();
            switch (text) {
                case ". . .": dots.setText(". ."); break;
                case ". .": dots.setText("."); break;
                case ".": dots.setText(". . ."); break;
            }
        });
        timer.start();

        // Back to home button
        gbc.gridy = 3;
        gbc.insets = new Insets(40, 20, 20, 20);
        JButton homeBtn = new JButton("Kembali ke Menu Utama");
        homeBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        homeBtn.setBackground(new Color(46, 125, 50));
        homeBtn.setForeground(Color.WHITE);
        homeBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        homeBtn.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));
        homeBtn.addActionListener(e -> {
            new OpeningForm().setVisible(true);
            dispose();
        });
        centerPanel.add(homeBtn, gbc);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Footer
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(new Color(0, 0, 0, 150));
        JLabel footer = new JLabel("Terima kasih telah berkunjung ke Poirot Cafe!");
        footer.setForeground(Color.WHITE);
        footer.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        footerPanel.add(footer);

        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }
}