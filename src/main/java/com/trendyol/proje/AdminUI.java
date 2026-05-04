package com.trendyol.proje;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import org.bson.Document;

public class AdminUI extends JFrame {
    DatabaseManager db = new DatabaseManager();
    DefaultTableModel model;
    JTable table;

    public AdminUI() {
        setTitle("Yönetici Paneli - Stok Takibi");
        setSize(1000, 600);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Style.BG_LIGHT);

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, Style.BORDER_COLOR),
                BorderFactory.createEmptyBorder(15, 25, 15, 25)
        ));

        JLabel titleLabel = new JLabel("Yönetici Paneli - Stok Takibi");
        titleLabel.setFont(Style.TITLE_FONT);
        titleLabel.setForeground(Style.DARK_GREY);
        headerPanel.add(titleLabel, BorderLayout.WEST);

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        actionPanel.setBackground(Color.WHITE);

        JButton btnUpdateStock = new JButton("Stoğu Güncelle");
        btnUpdateStock.setBackground(Color.WHITE);
        btnUpdateStock.setForeground(Style.ORANGE);
        btnUpdateStock.setFont(Style.BOLD_FONT);
        btnUpdateStock.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Style.ORANGE, 2),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        btnUpdateStock.setFocusPainted(false);
        btnUpdateStock.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnUpdateStock.addActionListener(e -> showUpdateStockDialog());

        JButton btnRefresh = new JButton("Yenile");
        btnRefresh.setBackground(Color.WHITE);
        btnRefresh.setForeground(Style.DARK_GREY);
        btnRefresh.setFont(Style.BOLD_FONT);
        btnRefresh.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Style.BORDER_COLOR, 2),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        btnRefresh.setFocusPainted(false);
        btnRefresh.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRefresh.addActionListener(e -> loadData());

        JButton btnSuppliers = new JButton("Tedarikçiler");
        btnSuppliers.setBackground(Style.ORANGE);
        btnSuppliers.setForeground(Color.WHITE);
        btnSuppliers.setFont(Style.BOLD_FONT);
        btnSuppliers.setBorder(BorderFactory.createEmptyBorder(12, 15, 12, 15));
        btnSuppliers.setFocusPainted(false);
        btnSuppliers.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSuppliers.addActionListener(e -> new SuppliersUI(this));

        JButton btnLogout = new JButton("Çıkış Yap");
        btnLogout.setBackground(new Color(255, 235, 235));
        btnLogout.setForeground(new Color(220, 50, 50));
        btnLogout.setFont(Style.BOLD_FONT);
        btnLogout.setBorder(BorderFactory.createEmptyBorder(12, 15, 12, 15));
        btnLogout.setFocusPainted(false);
        btnLogout.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogout.addActionListener(e -> {
            new LoginUI().setVisible(true);
            this.dispose();
        });

        actionPanel.add(btnUpdateStock);
        actionPanel.add(btnRefresh);
        actionPanel.add(btnSuppliers);
        actionPanel.add(btnLogout);
        headerPanel.add(actionPanel, BorderLayout.EAST);
        add(headerPanel, BorderLayout.NORTH);

        // --- TABLO MODELİ VE TASARIMI ---
        String[] columns = {"Ürün ID", "Ürün Adı", "Mevcut Stok", "Durum"};
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        table = new JTable(model);

        table.setFont(Style.CARD_FONT);
        table.setRowHeight(45);
        table.setGridColor(Style.BORDER_COLOR);
        table.setSelectionBackground(new Color(255, 235, 215));
        table.setSelectionForeground(Style.DARK_GREY);
        table.setShowVerticalLines(false);

        JTableHeader tHeader = table.getTableHeader();
        tHeader.setFont(Style.BOLD_FONT);
        tHeader.setBackground(Color.WHITE);
        tHeader.setForeground(Style.DARK_GREY);
        tHeader.setPreferredSize(new Dimension(100, 45));
        tHeader.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Style.ORANGE));

        DefaultTableCellRenderer customRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object v, boolean s, boolean f, int r, int c) {
                Component comp = super.getTableCellRendererComponent(t, v, s, f, r, c);
                setHorizontalAlignment(JLabel.CENTER);

                if (!s) {
                    int stock = 0;
                    try { stock = Integer.parseInt(t.getValueAt(r, 2).toString()); } catch (Exception ex) {}

                    if (stock <= 15) {
                        comp.setBackground(new Color(255, 235, 235));
                        comp.setForeground(new Color(220, 50, 50));
                        comp.setFont(Style.BOLD_FONT);
                    } else {
                        comp.setBackground(Color.WHITE);
                        comp.setForeground(Style.DARK_GREY);
                        comp.setFont(Style.CARD_FONT);
                    }
                }
                return comp;
            }
        };

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(customRenderer);
        }

        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.setBackground(Color.WHITE);
        tableContainer.setBorder(Style.CARD_BORDER);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);
        tableContainer.add(scrollPane, BorderLayout.CENTER);

        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.setBackground(Style.BG_LIGHT);
        wrapperPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 30, 30));
        wrapperPanel.add(tableContainer, BorderLayout.CENTER);

        add(wrapperPanel, BorderLayout.CENTER);

        loadData();
    }


    // --- STOK GÜNCELLEME PENCERESİ ---
    private void showUpdateStockDialog() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Lütfen stoğunu güncellemek istediğiniz ürünü tablodan seçin.", "Uyarı", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String productId = table.getValueAt(selectedRow, 0).toString();
        String productName = table.getValueAt(selectedRow, 1).toString();
        String currentStock = table.getValueAt(selectedRow, 2).toString();

        String newStockStr = JOptionPane.showInputDialog(this,
                productName + " için yeni stok miktarını girin:\n(Mevcut Stok: " + currentStock + ")",
                "Stok Güncelle",
                JOptionPane.PLAIN_MESSAGE);

        if (newStockStr != null && !newStockStr.trim().isEmpty()) {
            try {
                int newStock = Integer.parseInt(newStockStr);
                db.updateProductStock(productId, newStock);
                JOptionPane.showMessageDialog(this, "Stok başarıyla güncellendi!");
                loadData(); // Tabloyu yenile
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Lütfen geçerli bir tam sayı girin!", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // --- VERİLERİ VERİTABANINDAN ÇEKME ---
    void loadData() {
        model.setRowCount(0);
        for (Document d : db.getProducts()) {
            int stock = d.getInteger("stock") != null ? d.getInteger("stock") : 0;
            String status = stock <= 15 ? "Kritik Stok" : "Stok Yeterli";

            model.addRow(new Object[]{
                    d.getObjectId("_id").toString(),
                    d.getString("name"),
                    stock,
                    status
            });
        }
        table.revalidate();
        table.repaint();
    }
}