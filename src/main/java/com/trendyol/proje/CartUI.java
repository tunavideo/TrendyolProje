package com.trendyol.proje;

import javax.swing.*;
import java.awt.*;
import org.bson.Document;
import java.util.List;

public class CartUI extends JPanel {
    DatabaseManager db = new DatabaseManager();
    MainFrame frame;
    private JPanel itemsPanel;
    private JScrollPane scrollPane;
    private JButton btnCheckout;

    public CartUI(MainFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout(20, 20));
        setBackground(Style.BG_LIGHT);

        // --- BAŞLIK ---
        JLabel title = new JLabel("Alışveriş Sepetim", JLabel.CENTER);
        title.setFont(Style.TITLE_FONT);
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(title, BorderLayout.NORTH);

        // --- ALT KISIM: BUTONLAR PANELİ ---
        JPanel bottomPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        bottomPanel.setBackground(Style.BG_LIGHT);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20)); // Kenarlardan boşluk

        // 1. Geri Dön Butonu (İkincil Tasarım)
        JButton btnBack = new JButton("Alışverişe Devam Et");
        btnBack.setBackground(Color.WHITE);
        btnBack.setForeground(Style.ORANGE);
        btnBack.setFont(Style.BOLD_FONT);
        btnBack.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Style.ORANGE, 2),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        btnBack.setFocusPainted(false);
        btnBack.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnBack.addActionListener(e -> frame.showPage("Home")); // Ana sayfaya geçiş

        // 2. Ödemeye Geç Butonu (Ana Tasarım)
        btnCheckout = new JButton("ÖDEMEYE GEÇ (0.0 TL)");
        Style.applyPrimaryBtn(btnCheckout);
        btnCheckout.addActionListener(e -> {
            frame.mainPanel.add(new PaymentUI(frame), "Payment");
            frame.showPage("Payment");
        });

        bottomPanel.add(btnBack);
        bottomPanel.add(btnCheckout);
        add(bottomPanel, BorderLayout.SOUTH);

        refresh();
    }

    public void refresh() {
        if (scrollPane != null) remove(scrollPane);

        itemsPanel = new JPanel();
        itemsPanel.setLayout(new BoxLayout(itemsPanel, BoxLayout.Y_AXIS));
        itemsPanel.setBackground(Style.BG_LIGHT);

        double total = 0;
        List<Document> basket = db.getBasket();

        for (Document doc : basket) {
            JPanel itemCard = new JPanel(new BorderLayout(15, 0));
            Style.applyCardStyle(itemCard);
            itemCard.setMaximumSize(new Dimension(1200, 120));

            // 1. Resim Bölümü
            JLabel imgLabel = new JLabel();
            imgLabel.setHorizontalAlignment(SwingConstants.CENTER);

            String imagePath = doc.getString("imagePath");

            if (imagePath != null && !imagePath.isEmpty()) {
                try {
                    ImageIcon icon = new ImageIcon(imagePath);
                    Image img = icon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
                    imgLabel.setIcon(new ImageIcon(img));
                } catch (Exception e) {
                    imgLabel.setText("Hata");
                    imgLabel.setPreferredSize(new Dimension(80, 80));
                    imgLabel.setOpaque(true);
                    imgLabel.setBackground(Color.LIGHT_GRAY);
                }
            } else {
                imgLabel.setText("Resim Yok");
                imgLabel.setPreferredSize(new Dimension(80, 80));
                imgLabel.setOpaque(true);
                imgLabel.setBackground(Color.LIGHT_GRAY);
            }
            itemCard.add(imgLabel, BorderLayout.WEST);

            // 2. Metin Bölümü
            JPanel textPanel = new JPanel(new GridLayout(2, 1));
            textPanel.setOpaque(false);
            JLabel nameLabel = new JLabel(doc.getString("name"));
            nameLabel.setFont(Style.BOLD_FONT);
            JLabel priceLabel = new JLabel(doc.get("price") + " TL");
            priceLabel.setForeground(Style.ORANGE);
            textPanel.add(nameLabel);
            textPanel.add(priceLabel);
            itemCard.add(textPanel, BorderLayout.CENTER);

            // 3. Kaldır Butonu
            JButton btnRemove = new JButton("Kaldır");
            btnRemove.setBackground(new Color(220, 53, 69));
            btnRemove.setForeground(Color.WHITE);
            btnRemove.setFont(new Font("Segoe UI", Font.BOLD, 12));
            btnRemove.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
            btnRemove.setFocusPainted(false);
            btnRemove.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btnRemove.addActionListener(e -> {
                db.removeFromBasket(doc);
                refresh();
            });
            itemCard.add(btnRemove, BorderLayout.EAST);

            itemsPanel.add(itemCard);
            itemsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            total += doc.getDouble("price");
        }

        scrollPane = new JScrollPane(itemsPanel);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);

        // Fiyatı güncelle
        btnCheckout.setText("ÖDEMEYE GEÇ (" + total + " TL)");

        revalidate();
        repaint();
    }
}