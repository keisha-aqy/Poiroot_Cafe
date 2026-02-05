package view;

import javax.swing.*;
import java.awt.*;

public class PaymentForm extends JFrame {
    private String nomorPesanan;
    private double totalHarga;
    private String jenisPesanan;
    private Integer idMeja;

    // Palet Warna Standar Poirot Cafe
    private final Color COLOR_PRIMARY = new Color(139, 90, 43); // Coffee Brown
    private final Color COLOR_BG = new Color(250, 250, 245);    // Cream
    private final Color COLOR_SUCCESS = new Color(46, 125, 50); // Forest Green
    private final Color COLOR_INFO = new Color(120, 80, 40);    // Muted Brown

    public PaymentForm(String nomorPesanan, double totalHarga, String jenisPesanan, Integer idMeja) {
        this.nomorPesanan = nomorPesanan;
        this.totalHarga = totalHarga;
        this.jenisPesanan = jenisPesanan;
        this.idMeja = idMeja;

        setTitle("Poirot Cafe - Pembayaran");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        initUI();
    }

    private void initUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(COLOR_BG);

        // Header - Konsisten dengan MenuForm
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(COLOR_PRIMARY);
        headerPanel.setPreferredSize(new Dimension(getWidth(), 100));

        JLabel title = new JLabel("Pilih Metode Pembayaran", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        title.setForeground(Color.WHITE);
        headerPanel.add(title, BorderLayout.CENTER);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Info Panel - Dibuat lebih bersih
        JPanel infoPanel = new JPanel(new GridBagLayout());
        infoPanel.setBackground(Color.WHITE); // Panel putih di atas bg cream
        infoPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.insets = new Insets(5, 0, 5, 0);

        JLabel orderLabel = new JLabel("Nomor Pesanan: " + nomorPesanan);
        orderLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        infoPanel.add(orderLabel, gbc);

        gbc.gridy++;
        JLabel totalLabel = new JLabel("Total Tagihan: Rp " + String.format("%,.0f", totalHarga));
        totalLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        totalLabel.setForeground(COLOR_PRIMARY);
        infoPanel.add(totalLabel, gbc);

        mainPanel.add(infoPanel, BorderLayout.CENTER);

        // Payment options - Tombol sekarang lebih harmonis
        JPanel paymentPanel = new JPanel(new GridLayout(1, 2, 40, 0));
        paymentPanel.setBackground(COLOR_BG);
        paymentPanel.setBorder(BorderFactory.createEmptyBorder(40, 60, 60, 60));

        // Cash button (Sekarang menggunakan warna Muted Brown agar tidak tabrakan dengan biru)
        JButton cashBtn = createPaymentButton("TUNAI / CASH", "💵", COLOR_INFO);
        cashBtn.addActionListener(e -> completePayment("cash"));

        // QRIS button (Tetap hijau karena melambangkan teknologi/instan)
        JButton qrisBtn = createPaymentButton("QRIS / E-WALLET", "📱", COLOR_SUCCESS);
        qrisBtn.addActionListener(e -> showQRISDialog());

        paymentPanel.add(cashBtn);
        paymentPanel.add(qrisBtn);

        mainPanel.add(paymentPanel, BorderLayout.SOUTH);
        add(mainPanel);
    }

    private JButton createPaymentButton(String text, String icon, Color color) {
        JButton button = new JButton("<html><center><font size='+2'>" + icon + "</font><br><br>" + text + "</center></html>") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(getModel().isRollover() ? color.brighter() : color);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2.dispose();
                super.paintComponent(g);
            }
        };

        button.setFont(new Font("Segoe UI", Font.BOLD, 18));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }
    private void showQRISDialog() {
        JDialog qrisDialog = new JDialog(this, "Scan QRIS", true);
        qrisDialog.setSize(400, 500);
        qrisDialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JLabel title = new JLabel("Scan Kode QRIS", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // QR Code placeholder (in real app, generate actual QR)
        JPanel qrPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Draw QR code pattern
                int size = Math.min(getWidth(), getHeight()) - 40;
                int x = (getWidth() - size) / 2;
                int y = (getHeight() - size) / 2;

                g2d.setColor(Color.WHITE);
                g2d.fillRect(x, y, size, size);

                g2d.setColor(Color.BLACK);
                g2d.fillRect(x, y, size, size);
                g2d.setColor(Color.WHITE);
                g2d.fillRect(x + 10, y + 10, size - 20, size - 20);
                g2d.setColor(Color.BLACK);

                // Draw QR pattern
                for (int i = 0; i < 7; i++) {
                    for (int j = 0; j < 7; j++) {
                        if ((i + j) % 2 == 0) {
                            g2d.fillRect(x + 20 + i * 20, y + 20 + j * 20, 15, 15);
                        }
                    }
                }
            }
        };
        qrPanel.setPreferredSize(new Dimension(300, 300));

        JLabel amountLabel = new JLabel("Total: Rp " + String.format("%,.0f", totalHarga), SwingConstants.CENTER);
        amountLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));

        JButton confirmBtn = new JButton("Konfirmasi Pembayaran");
        confirmBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        confirmBtn.setBackground(new Color(46, 125, 50));
        confirmBtn.setForeground(Color.WHITE);
        confirmBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        confirmBtn.addActionListener(e -> {
            completePayment("qris");
            qrisDialog.dispose();
        });

        panel.add(title, BorderLayout.NORTH);
        panel.add(qrPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        bottomPanel.add(amountLabel, BorderLayout.NORTH);
        bottomPanel.add(confirmBtn, BorderLayout.SOUTH);

        panel.add(bottomPanel, BorderLayout.SOUTH);

        qrisDialog.add(panel);
        qrisDialog.setVisible(true);
    }

    private void completePayment(String metodeBayar) {
        // In real app, update payment method in database
        JOptionPane.showMessageDialog(this,
                "Pembayaran berhasil!\nMetode: " + metodeBayar.toUpperCase() +
                        "\nNomor Pesanan: " + nomorPesanan +
                        "\nTerima kasih atas pesanannya!",
                "Pembayaran Berhasil",
                JOptionPane.INFORMATION_MESSAGE);

        new EndingForm().setVisible(true);
        dispose();
    }
}