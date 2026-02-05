package view;

import dao.MejaDAO;
import model.Meja;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class DineInForm extends JFrame {
    private List<Meja> mejaList;

    public DineInForm() {
        setTitle("Poirot Cafe - Pilih Meja");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        // Get available tables
        MejaDAO mejaDAO = new MejaDAO();
        mejaList = mejaDAO.getAllMejaTersedia();

        // Debug: Tampilkan jumlah meja yang didapat
        System.out.println("Jumlah meja tersedia: " + mejaList.size());
        for (Meja meja : mejaList) {
            System.out.println("Meja: " + meja.getNomorMeja() + " - Status: " + meja.getStatus());
        }

        initUI();
    }

    private void initUI() {
        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 245, 245));

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(139, 90, 43));
        headerPanel.setPreferredSize(new Dimension(getWidth(), 100));
        headerPanel.setLayout(new BorderLayout());

        JLabel title = new JLabel("Pilih Meja", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        headerPanel.add(title, BorderLayout.CENTER);

        // Info label untuk jumlah meja tersedia
        JLabel infoLabel = new JLabel("Meja Tersedia: " + mejaList.size(), SwingConstants.RIGHT);
        infoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        infoLabel.setForeground(new Color(255, 255, 255, 200));
        infoLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
        headerPanel.add(infoLabel, BorderLayout.EAST);

        // Back button
        JButton backBtn = createStyledButton("← Kembali", new Color(120, 80, 40));
        backBtn.addActionListener(e -> {
            new OpeningForm().setVisible(true);
            dispose();
        });
        headerPanel.add(backBtn, BorderLayout.WEST);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Table selection panel - menggunakan FlowLayout atau GridBagLayout yang lebih fleksibel
        JPanel tablePanel = new JPanel(new GridBagLayout());
        tablePanel.setBackground(new Color(245, 245, 245));
        tablePanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.NONE;

        int columns = 3; // Jumlah kolom yang diinginkan
        int row = 0;
        int col = 0;

        for (Meja meja : mejaList) {
            gbc.gridx = col;
            gbc.gridy = row;

            JButton tableBtn = createTableButton(meja);
            tablePanel.add(tableBtn, gbc);

            col++;
            if (col >= columns) {
                col = 0;
                row++;
            }
        }

        // Tambahkan panel kosong untuk mengisi ruang jika jumlah meja tidak genap
        if (col != 0 && col < columns) {
            for (int i = col; i < columns; i++) {
                gbc.gridx = i;
                gbc.gridy = row;
                tablePanel.add(Box.createHorizontalStrut(200), gbc);
            }
        }

        JScrollPane scrollPane = new JScrollPane(tablePanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Legend panel untuk kapasitas meja
        JPanel legendPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        legendPanel.setBackground(new Color(245, 245, 245));
        legendPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                "Keterangan Kapasitas"
        ));

        legendPanel.add(createLegendItem("1-2 orang", new Color(144, 202, 249)));
        legendPanel.add(createLegendItem("3-4 orang", new Color(129, 199, 132)));
        legendPanel.add(createLegendItem("5+ orang", new Color(255, 183, 77)));

        mainPanel.add(legendPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel createLegendItem(String text, Color color) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        panel.setBackground(new Color(245, 245, 245));

        JLabel colorLabel = new JLabel("■");
        colorLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        colorLabel.setForeground(color);

        JLabel textLabel = new JLabel(text);
        textLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        panel.add(colorLabel);
        panel.add(textLabel);
        return panel;
    }

    private JButton createTableButton(Meja meja) {
        JButton button = new JButton();
        button.setLayout(new BorderLayout());
        button.setPreferredSize(new Dimension(200, 150));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(true);

        // Background color based on capacity
        Color bgColor;
        if (meja.getKapasitas() <= 2) {
            bgColor = new Color(144, 202, 249); // Light blue for small tables
        } else if (meja.getKapasitas() <= 4) {
            bgColor = new Color(129, 199, 132); // Light green for medium tables
        } else {
            bgColor = new Color(255, 183, 77); // Orange for large tables
        }

        button.setBackground(bgColor);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.DARK_GRAY, 2),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        // Table number
        JLabel tableNumber = new JLabel("Meja " + meja.getNomorMeja(), SwingConstants.CENTER);
        tableNumber.setFont(new Font("Segoe UI", Font.BOLD, 24));
        tableNumber.setForeground(Color.DARK_GRAY);

        // Table capacity
        JLabel capacity = new JLabel(meja.getKapasitas() + " orang", SwingConstants.CENTER);
        capacity.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        capacity.setForeground(Color.DARK_GRAY);

        // Status indicator
        JLabel status = new JLabel("✓ Tersedia", SwingConstants.CENTER);
        status.setFont(new Font("Segoe UI", Font.BOLD, 14));
        status.setForeground(new Color(0, 100, 0));

        JPanel contentPanel = new JPanel(new GridLayout(3, 1));
        contentPanel.setOpaque(false);
        contentPanel.add(tableNumber);
        contentPanel.add(capacity);
        contentPanel.add(status);

        button.add(contentPanel, BorderLayout.CENTER);

        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int response = JOptionPane.showConfirmDialog(
                        DineInForm.this,
                        "Pilih meja " + meja.getNomorMeja() + " untuk " + meja.getKapasitas() + " orang?",
                        "Konfirmasi Meja",
                        JOptionPane.YES_NO_OPTION
                );

                if (response == JOptionPane.YES_OPTION) {
                    new MenuForm("dine_in", meja.getIdMeja(), meja.getNomorMeja()).setVisible(true);
                    dispose();
                }
            }
        });

        return button;
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(true);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 1),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }
}