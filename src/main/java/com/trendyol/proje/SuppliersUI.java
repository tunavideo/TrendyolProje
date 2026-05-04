package com.trendyol.proje;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import org.bson.Document;
import java.util.List;

public class SuppliersUI extends JDialog {
    DatabaseManager db = new DatabaseManager();
    DefaultTableModel model;
    JTable table;

    public SuppliersUI(JFrame parent) {
        super(parent, "Tedarikçi Yönetimi", true);
        setSize(800, 500);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Style.BG_LIGHT);

        // --- BAŞLIK ---
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, Style.BORDER_COLOR),
                BorderFactory.createEmptyBorder(15, 25, 15, 25)
        ));

        JLabel titleLabel = new JLabel("📦 Tedarikçi Listesi");
        titleLabel.setFont(Style.TITLE_FONT);
        titleLabel.setForeground(Style.DARK_GREY);
        headerPanel.add(titleLabel, BorderLayout.WEST);

        JButton btnClose = new JButton("Kapat");
        btnClose.setBackground(new Color(255, 235, 235));
        btnClose.setForeground(new Color(220, 50, 50));
        btnClose.setFont(Style.BOLD_FONT);
        btnClose.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnClose.setFocusPainted(false);
        btnClose.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnClose.addActionListener(e -> dispose());
        headerPanel.add(btnClose, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);

        // --- TABLO ---
        String[] columns = {"Şirket Adı", "Kategori", "İletişim"};
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        table = new JTable(model);
        table.setFont(Style.CARD_FONT);
        table.setRowHeight(50);
        table.setGridColor(Style.BORDER_COLOR);
        table.setSelectionBackground(new Color(255, 235, 215));
        table.setSelectionForeground(Style.DARK_GREY);
        table.setShowVerticalLines(false);

        // Başlık tasarımı
        JTableHeader tHeader = table.getTableHeader();
        tHeader.setFont(Style.BOLD_FONT);
        tHeader.setBackground(Color.WHITE);
        tHeader.setForeground(Style.DARK_GREY);
        tHeader.setPreferredSize(new Dimension(100, 45));
        tHeader.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Style.ORANGE));

        // Hücreleri ortala
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Tablo konteynerı
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
        setVisible(true);
    }

    private void loadData() {
        model.setRowCount(0);
        List<Document> suppliers = db.getSuppliers();
        for (Document doc : suppliers) {
            model.addRow(new Object[]{
                    doc.getString("companyName") != null ? doc.getString("companyName") : "-",
                    doc.getString("category") != null ? doc.getString("category") : "-",
                    doc.getString("contact") != null ? doc.getString("contact") : "-"
            });
        }
        table.revalidate();
        table.repaint();
    }
}
