package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OpeningForm extends JFrame {
    public OpeningForm() {
        setTitle("Poirot Cafe - Welcome");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        // Main panel dengan BoxLayout untuk kontrol vertikal yang lebih baik
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(139, 90, 43)); // Background solid untuk debugging

        // Panel untuk logo/title (atas)
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridBagLayout());
        topPanel.setOpaque(false);
        topPanel.setPreferredSize(new Dimension(800, 300));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(50, 20, 30, 20);

        // Cafe name
        JLabel cafeName = new JLabel("Poirot Cafe");
        cafeName.setFont(new Font("Brush Script MT", Font.BOLD, 72));
        cafeName.setForeground(new Color(255, 215, 0));
        topPanel.add(cafeName, gbc);

        // Cafe quote
        gbc.gridy = 1;
        JLabel quote = new JLabel("Where coffee meets community, and every visit feels like home");
        quote.setFont(new Font("Segoe UI", Font.ITALIC, 18));
        quote.setForeground(Color.WHITE);
        topPanel.add(quote, gbc);

        mainPanel.add(topPanel);

        // Panel untuk tombol (tengah)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 30));
        buttonPanel.setOpaque(false);
        buttonPanel.setPreferredSize(new Dimension(800, 200));

        // Dine In button
        JButton dineInBtn = new JButton("DINE IN");
        styleButton(dineInBtn, new Color(46, 125, 50));
        dineInBtn.setPreferredSize(new Dimension(200, 60));
        dineInBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DineInForm().setVisible(true);
                dispose();
            }
        });

        // Take Away button
        JButton takeAwayBtn = new JButton("TAKE AWAY");
        styleButton(takeAwayBtn, new Color(21, 101, 192));
        takeAwayBtn.setPreferredSize(new Dimension(200, 60));
        takeAwayBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MenuForm("take_away", null, null).setVisible(true);
                dispose();
            }
        });

        buttonPanel.add(dineInBtn);
        buttonPanel.add(takeAwayBtn);

        mainPanel.add(buttonPanel);

        // Footer panel (bawah)
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(new Color(0, 0, 0, 150));
        footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        JLabel footer = new JLabel("© 2024 Poirot Cafe - All rights reserved");
        footer.setForeground(Color.WHITE);
        footer.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        footerPanel.add(footer);

        mainPanel.add(footerPanel);

        add(mainPanel);

        // Debug: Tampilkan semua komponen
        System.out.println("Number of components in mainPanel: " + mainPanel.getComponentCount());
        System.out.println("ButtonPanel components: " + buttonPanel.getComponentCount());
    }

    private void styleButton(JButton button, Color color) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 18));
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setFocusPainted(false);
        button.setBorderPainted(true);
        button.setContentAreaFilled(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 2),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));

        // Hover effect sederhana
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(color.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Set look and feel untuk tampilan yang lebih baik
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

                OpeningForm form = new OpeningForm();
                form.setVisible(true);

                // Debug: Print ukuran frame
                System.out.println("Frame size: " + form.getSize());
                System.out.println("Frame is visible: " + form.isVisible());

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}