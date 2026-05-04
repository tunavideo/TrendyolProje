package com.trendyol.proje;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import org.bson.Document;

public class OrderHistoryUI extends JPanel {
    DatabaseManager db = new DatabaseManager();
    JTable table;
    DefaultTableModel model;

    public OrderHistoryUI(MainFrame frame) {
        setLayout(new BorderLayout(20, 20));
        setBackground(Style.BG_LIGHT);
        setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // --- ÜST BAŞLIK ---
        JLabel titleLabel = new JLabel("Sipariş Geçmişim", JLabel.CENTER);
        titleLabel.setFont(Style.TITLE_FONT);
        titleLabel.setForeground(Style.DARK_GREY);
        add(titleLabel, BorderLayout.NORTH);

        // --- TABLO MODELİ ---
        String[] columns = {"Sipariş No", "Tarih", "Toplam Tutar", "Ödeme Tipi"};
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        table = new JTable(model);

        // --- MODERN TABLO TASARIMI ---
        table.setFont(Style.CARD_FONT);
        table.setRowHeight(45);
        table.setGridColor(Style.BORDER_COLOR);
        table.setSelectionBackground(new Color(255, 235, 215));
        table.setSelectionForeground(Style.DARK_GREY);
        table.setShowVerticalLines(false);

        // --- METİNLERİ HÜCRE İÇİNDE ORTALAMA İŞLEMİ ---
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // --- TABLO BAŞLIĞI (HEADER) TASARIMI ---
        JTableHeader header = table.getTableHeader();
        header.setFont(Style.BOLD_FONT);
        header.setBackground(Color.WHITE);
        header.setForeground(Style.DARK_GREY);
        header.setPreferredSize(new Dimension(100, 40));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Style.ORANGE));

        refreshData(frame);

        // --- TABLOYU KART İÇİNE ALMA ---
        JPanel tableCard = new JPanel(new BorderLayout());
        tableCard.setBackground(Color.WHITE);
        tableCard.setBorder(Style.CARD_BORDER);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);

        tableCard.add(scrollPane, BorderLayout.CENTER);
        add(tableCard, BorderLayout.CENTER);

        // --- ALT KISIM: GERİ DÖNÜŞ BUTONU ---
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBackground(Style.BG_LIGHT);

        JButton btnBack = new JButton("Ana Sayfaya Dön");
        Style.applyPrimaryBtn(btnBack); // Butona markanın ana turuncu stilini verdik

        // Butona tıklanınca Home (UserHomeUI) sayfasına geçiş yap
        btnBack.addActionListener(e -> frame.showPage("Home"));

        bottomPanel.add(btnBack);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    public void refreshData(MainFrame frame) {
        model.setRowCount(0);
        for (Document doc : db.getOrderHistory()) {
            model.addRow(new Object[]{
                    doc.getObjectId("_id").toString().substring(18), // Kısa ID
                    doc.get("date"),
                    doc.get("total") + " TL",
                    doc.get("paymentMethod")
            });
        }
    }

    public void refresh() {
        refreshData(null);
        revalidate();
        repaint();
    }
}