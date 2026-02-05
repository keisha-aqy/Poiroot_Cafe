// MenuForm.java
package view;

import dao.MenuDAO;
import dao.PesananDAO;
import dao.MejaDAO;
import model.Menu;
import model.Pesanan;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuForm extends JFrame {
    private String jenisPesanan;
    private Integer idMeja;
    private String nomorMeja;
    private List<Menu> menuList;
    private List<Menu> selectedItems;
    private Map<Integer, JSpinner> spinnerMap;
    private DefaultTableModel cartTableModel;
    private JLabel totalLabel;
    private double totalHarga;
    private DecimalFormat currencyFormat;

    public MenuForm(String jenisPesanan, Integer idMeja, String nomorMeja) {
        this.jenisPesanan = jenisPesanan;
        this.idMeja = idMeja;
        this.nomorMeja = nomorMeja;
        this.selectedItems = new ArrayList<>();
        this.spinnerMap = new HashMap<>();
        this.totalHarga = 0;
        this.currencyFormat = new DecimalFormat("Rp #,###");

        setTitle("Poirot Cafe - Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);

        // Load menu data
        MenuDAO menuDAO = new MenuDAO();
        menuList = menuDAO.getAllMenu();

        initUI();
    }

    private void initUI() {
        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(250, 250, 245));

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(139, 90, 43));
        headerPanel.setPreferredSize(new Dimension(getWidth(), 80));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel title = new JLabel("Menu Poirot Cafe");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(Color.WHITE);

        JLabel orderType = new JLabel(jenisPesanan.equals("dine_in") ?
                "Dine In - Meja: " + nomorMeja : "Take Away");
        orderType.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        orderType.setForeground(Color.WHITE);

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setOpaque(false);
        titlePanel.add(title);
        titlePanel.add(Box.createHorizontalStrut(20));
        titlePanel.add(orderType);

        // Back button
        JButton backBtn = new JButton("← Kembali");
        backBtn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        backBtn.setBackground(new Color(120, 80, 40));
        backBtn.setForeground(Color.WHITE);
        backBtn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        backBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backBtn.addActionListener(e -> {
            if (jenisPesanan.equals("dine_in")) {
                new DineInForm().setVisible(true);
            } else {
                new OpeningForm().setVisible(true);
            }
            dispose();
        });

        headerPanel.add(titlePanel, BorderLayout.WEST);
        headerPanel.add(backBtn, BorderLayout.EAST);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Content panel (Menu + Cart)
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(700);
        splitPane.setDividerSize(3);

        // Left panel - Menu
        JPanel menuPanel = new JPanel(new BorderLayout());
        menuPanel.setBackground(Color.WHITE);
        menuPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 10));

        // Category tabs
        JTabbedPane categoryTabs = new JTabbedPane();
        categoryTabs.setFont(new Font("Segoe UI", Font.BOLD, 14));

        // Group menu by category
        Map<String, List<Menu>> menuByCategory = new HashMap<>();
        for (Menu menu : menuList) {
            menuByCategory.computeIfAbsent(menu.getKategori(), k -> new ArrayList<>()).add(menu);
        }

        // Create panels for each category
        for (String category : menuByCategory.keySet()) {
            JPanel categoryPanel = createCategoryPanel(menuByCategory.get(category));
            categoryTabs.addTab(category, categoryPanel);
        }

        menuPanel.add(categoryTabs, BorderLayout.CENTER);

        // Right panel - Cart
        JPanel cartPanel = new JPanel(new BorderLayout());
        cartPanel.setBackground(new Color(240, 240, 235));
        cartPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(20, 10, 20, 20),
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(new Color(200, 200, 200), 2),
                        "Keranjang Pesanan",
                        TitledBorder.LEFT,
                        TitledBorder.TOP,
                        new Font("Segoe UI", Font.BOLD, 16),
                        new Color(70, 70, 70)
                )
        ));

        // Customer name field
        JPanel customerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        customerPanel.setBackground(new Color(240, 240, 235));
        JLabel nameLabel = new JLabel("Nama Pelanggan:");
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        JTextField nameField = new JTextField(20);
        nameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        customerPanel.add(nameLabel);
        customerPanel.add(nameField);

        cartPanel.add(customerPanel, BorderLayout.NORTH);

        // Cart table
        String[] columnNames = {"Menu", "Jumlah", "Harga", "Subtotal"};
        cartTableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable cartTable = new JTable(cartTableModel);
        cartTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cartTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        cartTable.setRowHeight(30);
        cartTable.getColumnModel().getColumn(0).setPreferredWidth(200);
        cartTable.getColumnModel().getColumn(1).setPreferredWidth(80);
        cartTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        cartTable.getColumnModel().getColumn(3).setPreferredWidth(100);

        JScrollPane tableScroll = new JScrollPane(cartTable);
        tableScroll.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        cartPanel.add(tableScroll, BorderLayout.CENTER);

        // Total and buttons panel
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(new Color(240, 240, 235));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        // Total price
        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        totalPanel.setBackground(new Color(240, 240, 235));
        JLabel totalText = new JLabel("Total: ");
        totalText.setFont(new Font("Segoe UI", Font.BOLD, 18));
        totalLabel = new JLabel(currencyFormat.format(totalHarga));
        totalLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        totalLabel.setForeground(new Color(46, 125, 50));
        totalPanel.add(totalText);
        totalPanel.add(totalLabel);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        buttonPanel.setBackground(new Color(240, 240, 235));

        JButton clearBtn = new JButton("Clear");
        clearBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        clearBtn.setBackground(new Color(239, 83, 80));
        clearBtn.setForeground(Color.WHITE);
        clearBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        clearBtn.addActionListener(e -> {
            clearCart();
            nameField.setText("");
        });

        JButton confirmBtn = new JButton("Konfirmasi Pesanan");
        confirmBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        confirmBtn.setBackground(new Color(46, 125, 50));
        confirmBtn.setForeground(Color.WHITE);
        confirmBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        confirmBtn.addActionListener(e -> {
            if (selectedItems.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Keranjang kosong! Silakan pilih menu terlebih dahulu.",
                        "Peringatan",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            String customerName = nameField.getText().trim();
            if (customerName.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Silakan masukkan nama pelanggan.",
                        "Peringatan",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            int response = JOptionPane.showConfirmDialog(this,
                    "Konfirmasi pesanan untuk " + customerName + "?\nTotal: " + currencyFormat.format(totalHarga),
                    "Konfirmasi Pesanan",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            if (response == JOptionPane.YES_OPTION) {
                // Save order
                Pesanan pesanan = new Pesanan();
                PesananDAO pesananDAO = new PesananDAO();

                pesanan.setNomorPesanan(pesananDAO.generateNomorPesanan());
                pesanan.setNamaPelanggan(customerName);
                pesanan.setJenisPesanan(jenisPesanan);
                pesanan.setIdMeja(idMeja);
                pesanan.setTotalHarga(totalHarga);
                pesanan.setStatusPesanan("diproses");
                pesanan.setItems(selectedItems);

                int idPesanan = pesananDAO.createPesanan(pesanan);
                if (idPesanan > 0) {
                    boolean detailSuccess = pesananDAO.createDetailPesanan(idPesanan, selectedItems);
                    if (detailSuccess && idMeja != null) {
                        // Update table status
                        MejaDAO mejaDAO = new MejaDAO();
                        mejaDAO.updateStatusMeja(idMeja, "terpakai");
                    }

                    // Go to payment form
                    new PaymentForm(pesanan.getNomorPesanan(), totalHarga, jenisPesanan, idMeja).setVisible(true);
                    dispose();
                }
            }
        });

        buttonPanel.add(clearBtn);
        buttonPanel.add(confirmBtn);

        bottomPanel.add(totalPanel, BorderLayout.NORTH);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

        cartPanel.add(bottomPanel, BorderLayout.SOUTH);

        splitPane.setLeftComponent(menuPanel);
        splitPane.setRightComponent(cartPanel);

        mainPanel.add(splitPane, BorderLayout.CENTER);

        add(mainPanel);
    }

    private JPanel createCategoryPanel(List<Menu> menus) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(250, 250, 245));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        for (Menu menu : menus) {
            JPanel menuItemPanel = createMenuItemPanel(menu);
            panel.add(menuItemPanel, gbc);
            gbc.gridy++;
        }

        // Add empty space at bottom
        gbc.weighty = 1.0;
        panel.add(Box.createVerticalGlue(), gbc);

        return panel;
    }

    private JPanel createMenuItemPanel(Menu menu) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        panel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Menu info
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setBackground(Color.WHITE);

        JLabel nameLabel = new JLabel(menu.getNamaMenu());
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));

        JLabel priceLabel = new JLabel(currencyFormat.format(menu.getHarga()));
        priceLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        priceLabel.setForeground(new Color(46, 125, 50));

        JLabel stockLabel = new JLabel("Stok: " + menu.getStok());
        stockLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        stockLabel.setForeground(Color.GRAY);

        infoPanel.add(nameLabel, BorderLayout.NORTH);
        infoPanel.add(priceLabel, BorderLayout.CENTER);
        infoPanel.add(stockLabel, BorderLayout.SOUTH);

        // Quantity control
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        controlPanel.setBackground(Color.WHITE);

        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(0, 0, menu.getStok(), 1);
        JSpinner spinner = new JSpinner(spinnerModel);
        spinner.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        spinner.setPreferredSize(new Dimension(60, 30));
        spinnerMap.put(menu.getIdMenu(), spinner);

        spinner.addChangeListener(e -> {
            int quantity = (int) spinner.getValue();
            updateCartItem(menu, quantity);
        });

        JButton addBtn = new JButton("+");
        addBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        addBtn.setBackground(new Color(46, 125, 50));
        addBtn.setForeground(Color.WHITE);
        addBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addBtn.setPreferredSize(new Dimension(40, 30));
        addBtn.addActionListener(e -> {
            int current = (int) spinner.getValue();
            if (current < menu.getStok()) {
                spinner.setValue(current + 1);
            }
        });

        JButton removeBtn = new JButton("-");
        removeBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        removeBtn.setBackground(new Color(239, 83, 80));
        removeBtn.setForeground(Color.WHITE);
        removeBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        removeBtn.setPreferredSize(new Dimension(40, 30));
        removeBtn.addActionListener(e -> {
            int current = (int) spinner.getValue();
            if (current > 0) {
                spinner.setValue(current - 1);
            }
        });

        controlPanel.add(removeBtn);
        controlPanel.add(spinner);
        controlPanel.add(addBtn);

        panel.add(infoPanel, BorderLayout.CENTER);
        panel.add(controlPanel, BorderLayout.EAST);

        return panel;
    }

    private void updateCartItem(Menu menu, int quantity) {
        // Find existing item
        Menu existingItem = null;
        for (Menu item : selectedItems) {
            if (item.getIdMenu() == menu.getIdMenu()) {
                existingItem = item;
                break;
            }
        }

        if (quantity > 0) {
            if (existingItem != null) {
                existingItem.setJumlahPesan(quantity);
            } else {
                Menu newItem = new Menu(
                        menu.getIdMenu(),
                        menu.getNamaMenu(),
                        menu.getKategori(),
                        menu.getHarga(),
                        menu.getStok()
                );
                newItem.setJumlahPesan(quantity);
                selectedItems.add(newItem);
            }
        } else {
            selectedItems.remove(existingItem);
        }

        updateCartTable();
    }

    private void updateCartTable() {
        cartTableModel.setRowCount(0);
        totalHarga = 0;

        for (Menu item : selectedItems) {
            double subtotal = item.getSubtotal();
            totalHarga += subtotal;

            Object[] row = {
                    item.getNamaMenu(),
                    item.getJumlahPesan(),
                    currencyFormat.format(item.getHarga()),
                    currencyFormat.format(subtotal)
            };
            cartTableModel.addRow(row);
        }

        totalLabel.setText(currencyFormat.format(totalHarga));
    }

    private void clearCart() {
        selectedItems.clear();
        for (JSpinner spinner : spinnerMap.values()) {
            spinner.setValue(0);
        }
        updateCartTable();
    }
}