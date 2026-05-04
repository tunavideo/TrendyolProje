package com.trendyol.proje;

import javax.swing.*;
import java.awt.*;
import org.bson.Document;

public class FavoritesUI extends JPanel {
    DatabaseManager db = new DatabaseManager();
    MainFrame frame;
    private JScrollPane scrollPane;

    // --- YENİ ZARİF BOYUTLAR (Bir tık daha küçük ve estetik) ---
    private static final Dimension CARD_SIZE = new Dimension(220, 310);
    private static final Dimension IMAGE_SIZE = new Dimension(180, 160);

    public FavoritesUI(MainFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout());
        setBackground(Style.BG_LIGHT);

        // --- BAŞLIK ---
        JLabel title = new JLabel("Favorilerim ❤", JLabel.CENTER);
        title.setFont(Style.TITLE_FONT);
        title.setBorder(BorderFactory.createEmptyBorder(25, 0, 15, 0));
        add(title, BorderLayout.NORTH);

        // --- ALT BAR: BUTON ---
        JPanel botPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        botPanel.setBackground(Style.BG_LIGHT);
        botPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));

        JButton btnBack = new JButton("Alışverişe Devam Et");
        Style.applyPrimaryBtn(btnBack);
        btnBack.addActionListener(e -> frame.showPage("Home"));

        botPanel.add(btnBack);
        add(botPanel, BorderLayout.SOUTH);

        refresh();
    }

    public void refresh() {
        if (scrollPane != null) remove(scrollPane);

        // --- ANA TAŞIYICI ---
        JPanel container = new JPanel();
        container.setLayout(new BorderLayout());
        container.setBackground(Style.BG_LIGHT);

        // --- GRID PANELİ: YAN YANA MAX 4 KUTU ---
        // hGap ve vGap 20 yaparak kutuları birbirine biraz daha yaklaştırdık
        JPanel favGrid = new JPanel(new GridLayout(0, 4, 20, 25));
        favGrid.setBackground(Style.BG_LIGHT);
        favGrid.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));

        for (Document doc : db.getFavorites()) {
            JPanel card = createCompactCard(doc);
            favGrid.add(card);
        }

        // --- KRİTİK NOKTA: Kutuların devleşmemesi için bir üst panel ---
        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));
        wrapper.setBackground(Style.BG_LIGHT);
        wrapper.add(favGrid);

        container.add(wrapper, BorderLayout.NORTH);

        scrollPane = new JScrollPane(container);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Daha akıcı kaydırma
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        add(scrollPane, BorderLayout.CENTER);

        revalidate();
        repaint();
    }

    private JPanel createCompactCard(Document doc) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        // Boyutları burada sabitliyoruz
        card.setPreferredSize(CARD_SIZE);
        card.setMaximumSize(CARD_SIZE);
        card.setMinimumSize(CARD_SIZE);
        card.setBackground(Color.WHITE);

        Style.applyCardStyle(card); // Style.java'daki gölge ve kenarlık

        // 1. Resim Alanı
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        JLabel imgLabel = new JLabel();
        imgLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        imgLabel.setPreferredSize(IMAGE_SIZE);
        imgLabel.setMaximumSize(IMAGE_SIZE);

        String imagePath = doc.getString("imagePath");

        if (imagePath != null && !imagePath.isEmpty()) {
            try {
                ImageIcon icon = new ImageIcon(imagePath);
                Image img = icon.getImage().getScaledInstance(IMAGE_SIZE.width, IMAGE_SIZE.height, Image.SCALE_SMOOTH);
                imgLabel.setIcon(new ImageIcon(img));
            } catch (Exception e) {
                imgLabel.setText("Görsel Yüklenemedi");
                imgLabel.setPreferredSize(IMAGE_SIZE);
                imgLabel.setOpaque(true);
                imgLabel.setBackground(new Color(245, 245, 245));
            }
        } else {
            imgLabel.setText("Görsel Yok");
            imgLabel.setPreferredSize(IMAGE_SIZE);
            imgLabel.setOpaque(true);
            imgLabel.setBackground(new Color(245, 245, 245));
        }

        card.add(imgLabel);

        // 2. Ürün İsmi
        card.add(Box.createRigidArea(new Dimension(0, 12)));
        JLabel nameLabel = new JLabel(doc.getString("name"), JLabel.CENTER);
        nameLabel.setFont(Style.BOLD_FONT);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        if (nameLabel.getText().length() > 22) {
            nameLabel.setText(nameLabel.getText().substring(0, 19) + "...");
        }
        card.add(nameLabel);

        // 3. Fiyat
        card.add(Box.createRigidArea(new Dimension(0, 5)));
        JLabel priceLabel = new JLabel(doc.get("price") + " TL", JLabel.CENTER);
        priceLabel.setFont(Style.PRICE_FONT);
        priceLabel.setForeground(Style.ORANGE);
        priceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(priceLabel);

        // 4. Favoriden Kaldır Butonu
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        JButton btnRemove = new JButton("Favoriden Kaldır");
        btnRemove.setBackground(new Color(220, 53, 69));
        btnRemove.setForeground(Color.WHITE);
        btnRemove.setFont(new Font("Segoe UI", Font.BOLD, 11));
        btnRemove.setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10));
        btnRemove.setFocusPainted(false);
        btnRemove.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRemove.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnRemove.setMaximumSize(new Dimension(180, 30));
        btnRemove.addActionListener(e -> {
            db.removeFromFavorites(doc);
            refresh();
        });
        card.add(btnRemove);

        card.add(Box.createVerticalGlue()); // İçeriği yukarıda tutar
        return card;
    }
}