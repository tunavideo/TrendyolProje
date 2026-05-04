package com.trendyol.proje;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import org.bson.Document;
import java.util.List;

public class UserHomeUI extends JPanel {
    private MainFrame frame;

    public UserHomeUI(MainFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout());
        setBackground(Style.BG_LIGHT); // Sayfa arka planı

        // --- ÜST MENÜ (HEADER) TASARIMI ---
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, Style.BORDER_COLOR), // Alt çizgi
                BorderFactory.createEmptyBorder(10, 20, 10, 20) // İç boşluk
        ));

        JLabel logoLabel = new JLabel("Ana Sayfa");
        logoLabel.setFont(Style.TITLE_FONT);
        logoLabel.setForeground(Style.DARK_GREY);
        headerPanel.add(logoLabel, BorderLayout.WEST);

        // Menü Butonları
        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        topBar.setBackground(Color.WHITE);

        String[] buttons = {"Favoriler", "Sepet", "Sipariş Geçmişi", "Kargo Takip"};
        for (String b : buttons) {
            JButton btn = new JButton(b);
            Style.applyPrimaryBtn(btn); // Butonlara Turuncu Hover Efekti
            btn.addActionListener(e -> {
                if(b.equals("Kargo Takip")) {
                    showCargoTrackingDialog();
                } else {
                    frame.showPage(b.equals("Sipariş Geçmişi") ? "History" : b.equals("Sepet") ? "Cart" : "Favorites");
                }
            });
            topBar.add(btn);
        }
        headerPanel.add(topBar, BorderLayout.EAST);
        add(headerPanel, BorderLayout.NORTH);

        // --- ÜRÜNLER ALANI (GRID YAPI) ---
        // Kartların dikeyde sünmemesi için bir taşıyıcı panel oluşturuyoruz
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBackground(Style.BG_LIGHT);

        JPanel productGrid = new JPanel(new GridLayout(0, 4, 25, 25)); // 4 Sütunlu ferah grid
        productGrid.setBackground(Style.BG_LIGHT);
        productGrid.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        for (Document doc : frame.db.getProducts()) {
            productGrid.add(createProductCard(doc, frame));
        }

        container.add(productGrid);
        container.add(Box.createVerticalGlue()); // Alt boşlukları korur, kartları uzatmaz

        JScrollPane scrollPane = new JScrollPane(container);
        scrollPane.setBorder(null); // Çirkin kenarlıkları kaldır
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane, BorderLayout.CENTER);
    }

    // --- MODERN ÜRÜN KARTI TASARIMI ---
    private JPanel createProductCard(Document doc, MainFrame frame) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setPreferredSize(new Dimension(260, 360));
        card.setMaximumSize(new Dimension(260, 360));
        Style.applyCardStyle(card); // Kart Gölgelendirme ve Hover Efekti

        // 1. Resim Bölümü
        JLabel imgLabel = new JLabel();
        imgLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        imgLabel.setHorizontalAlignment(JLabel.CENTER);

        String imagePath = doc.getString("imagePath");
        if (imagePath != null && !imagePath.isEmpty()) {
            try {
                ImageIcon icon = new ImageIcon(imagePath);
                Image img = icon.getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH);
                imgLabel.setIcon(new ImageIcon(img));
            } catch (Exception e) {
                imgLabel.setText("Görsel Yüklenemedi");
                imgLabel.setPreferredSize(new Dimension(180, 180));
                imgLabel.setOpaque(true);
                imgLabel.setBackground(new Color(245, 245, 245));
            }
        } else {
            imgLabel.setText("Görsel Yok");
            imgLabel.setPreferredSize(new Dimension(180, 180));
            imgLabel.setOpaque(true);
            imgLabel.setBackground(new Color(245, 245, 245));
        }
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(imgLabel);

        // 2. Ürün İsmi
        card.add(Box.createRigidArea(new Dimension(0, 15)));
        JLabel nameLabel = new JLabel(doc.getString("name"), JLabel.CENTER);
        nameLabel.setFont(Style.BOLD_FONT);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        if (nameLabel.getText().length() > 22) {
            nameLabel.setText(nameLabel.getText().substring(0, 20) + "...");
        }
        card.add(nameLabel);

        // 3. Fiyat
        card.add(Box.createRigidArea(new Dimension(0, 5)));
        JLabel priceLabel = new JLabel(doc.getDouble("price") + " TL", JLabel.CENTER);
        priceLabel.setFont(Style.PRICE_FONT);
        priceLabel.setForeground(Style.ORANGE);
        priceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(priceLabel);

        // 4. Aksiyon Butonları (Yan Yana)
        card.add(Box.createRigidArea(new Dimension(0, 15)));
        JPanel btnPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        btnPanel.setOpaque(false);
        btnPanel.setMaximumSize(new Dimension(220, 35));

        JButton addFav = new JButton("❤ Favori");
        // Favori butonu ikincil buton (Beyaz arka plan, turuncu yazı)
        addFav.setBackground(Color.WHITE);
        addFav.setForeground(Style.ORANGE);
        addFav.setFont(new Font("Segoe UI", Font.BOLD, 12));
        addFav.setBorder(BorderFactory.createLineBorder(Style.ORANGE, 1));
        addFav.setFocusPainted(false);
        addFav.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JButton addCart = new JButton("Sepete Ekle");
        Style.applyPrimaryBtn(addCart); // Ana Buton (Tam Turuncu)
        addCart.setFont(new Font("Segoe UI", Font.BOLD, 12));
        addCart.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        addFav.addActionListener(e -> frame.db.addToFavorites(doc));
        addCart.addActionListener(e -> frame.db.addToBasket(doc));

        btnPanel.add(addFav);
        btnPanel.add(addCart);
        card.add(btnPanel);

        card.add(Box.createRigidArea(new Dimension(0, 15))); // Alt boşluk

        return card;
    }

    // --- KARGO TAKİP DİYALOĞU (MODERN TABLO İLE) ---
    private void showCargoTrackingDialog() {
        JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), "Kargo Takip", true);
        dialog.setSize(700, 450);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.getContentPane().setBackground(Style.BG_LIGHT);

        JLabel header = new JLabel("Kargo Durumunu Görüntülemek İçin Bir Siparişe Tıklayın", JLabel.CENTER);
        header.setFont(Style.CARD_TITLE_FONT);
        header.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));
        dialog.add(header, BorderLayout.NORTH);

        String[] columns = {"Sipariş No", "Ürünler", "Kargo Durumu"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        List<Document> orders = frame.db.getOrderHistory();
        for (Document doc : orders) {
            model.addRow(new Object[]{
                    doc.getObjectId("_id").toString(),
                    doc.getString("details"),
                    doc.getString("status")
            });
        }

        JTable table = new JTable(model);

        table.setFont(Style.CARD_FONT);
        table.setRowHeight(40);
        table.setSelectionBackground(new Color(255, 235, 215));
        table.setSelectionForeground(Style.DARK_GREY);
        table.setShowVerticalLines(false);
        table.setGridColor(Style.BORDER_COLOR);

        // Hücreleri Ortala (Görseldeki kayma sorununu engeller)
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Başlık (Header) Tasarımı
        JTableHeader tHeader = table.getTableHeader();
        tHeader.setFont(Style.BOLD_FONT);
        tHeader.setBackground(Color.WHITE);
        tHeader.setPreferredSize(new Dimension(100, 40));
        tHeader.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Style.ORANGE));

        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 1) {
                    int row = table.getSelectedRow();
                    if (row >= 0) {
                        String orderId = table.getValueAt(row, 0).toString();
                        String details = table.getValueAt(row, 1).toString();
                        CargoTrackingUI cargoUI = new CargoTrackingUI(orderId, details);
                        cargoUI.setAlwaysOnTop(true);
                        cargoUI.toFront();
                        cargoUI.requestFocus();
                        SwingUtilities.invokeLater(() -> {
                            cargoUI.setAlwaysOnTop(false);
                        });
                        dialog.dispose();
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);

        // Tabloyu İçeri Alma Paneli
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
        tablePanel.setBackground(Style.BG_LIGHT);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        dialog.add(tablePanel, BorderLayout.CENTER);
        dialog.setVisible(true);
    }
}