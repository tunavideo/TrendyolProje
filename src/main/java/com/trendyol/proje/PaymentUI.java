package com.trendyol.proje;

import javax.swing.*;
import java.awt.*;
import org.bson.Document;
import java.util.List;

public class PaymentUI extends JPanel {
    MainFrame frame;
    DatabaseManager db = new DatabaseManager();

    public PaymentUI(MainFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout(0, 0));
        setBackground(Style.BG_LIGHT);

        // --- SOL TARAF (ADRES VE ÖDEME) ---
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(Style.BG_LIGHT);
        leftPanel.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));

        // 1. Adres Bölümü
        JLabel addrTitle = new JLabel("Teslimat Adresi");
        addrTitle.setFont(Style.BOLD_FONT);
        addrTitle.setForeground(Style.DARK_GREY);
        addrTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextArea addrField = new JTextArea("Üniversite Mah. KYK Yurdu...");
        addrField.setFont(Style.CARD_FONT);
        addrField.setLineWrap(true);
        addrField.setWrapStyleWord(true);

        JScrollPane addrScroll = new JScrollPane(addrField);
        addrScroll.setPreferredSize(new Dimension(400, 80));
        addrScroll.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        addrScroll.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Style.BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        addrScroll.setAlignmentX(Component.LEFT_ALIGNMENT);

        // 2. Ödeme Yöntemi Bölümü
        JLabel payTitle = new JLabel("Ödeme Yöntemi");
        payTitle.setFont(Style.BOLD_FONT);
        payTitle.setForeground(Style.DARK_GREY);
        payTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel methodPanel = new JPanel(new GridLayout(2, 1, 0, 15));
        methodPanel.setBackground(Style.BG_LIGHT);
        methodPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 140));
        methodPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Modern Radyo Butonları
        JRadioButton cardPay = new JRadioButton("  Kredi / Banka Kartı");
        JRadioButton cashPay = new JRadioButton("  Kapıda Nakit Ödeme");

        setupRadioButton(cardPay);
        setupRadioButton(cashPay);

        // Seçim Renklendirmesi (Görsel Geri Bildirim)
        cardPay.addActionListener(e -> {
            cardPay.setForeground(Style.ORANGE);
            cashPay.setForeground(Style.DARK_GREY);
        });

        cashPay.addActionListener(e -> {
            cashPay.setForeground(Style.ORANGE);
            cardPay.setForeground(Style.DARK_GREY);
        });

        // Başlangıç Durumu
        cardPay.setSelected(true);
        cardPay.setForeground(Style.ORANGE);

        ButtonGroup group = new ButtonGroup();
        group.add(cardPay);
        group.add(cashPay);

        methodPanel.add(cardPay);
        methodPanel.add(cashPay);

        // Sol paneli birleştirme
        leftPanel.add(addrTitle);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        leftPanel.add(addrScroll);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        leftPanel.add(payTitle);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        leftPanel.add(methodPanel);
        leftPanel.add(Box.createVerticalGlue());

        // --- SAĞ TARAF (SİPARİŞ ÖZETİ) ---
        JPanel summaryPanel = new JPanel();
        summaryPanel.setLayout(new BorderLayout());
        summaryPanel.setPreferredSize(new Dimension(380, 0));
        summaryPanel.setBackground(Color.WHITE);
        summaryPanel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Style.BORDER_COLOR));

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(30, 25, 30, 25));

        JLabel sumTitle = new JLabel("Sipariş Özeti");
        sumTitle.setFont(Style.TITLE_FONT);
        sumTitle.setForeground(Style.DARK_GREY);
        sumTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(sumTitle);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 25)));

        // Ürün Listeleme
        double calculatedTotal = 0.0;
        List<Document> basketItems = db.getBasket();
        StringBuilder productNamesBuilder = new StringBuilder();

        for (int i = 0; i < basketItems.size(); i++) {
            Document doc = basketItems.get(i);
            String itemName = doc.getString("name");
            double itemPrice = (doc.get("price") instanceof Integer) ?
                    ((Integer)doc.get("price")).doubleValue() : doc.getDouble("price");

            calculatedTotal += itemPrice;
            productNamesBuilder.append(itemName).append(i < basketItems.size() - 1 ? ", " : "");

            JPanel itemRow = new JPanel(new BorderLayout());
            itemRow.setBackground(Color.WHITE);
            itemRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));

            JLabel nameLabel = new JLabel(itemName);
            nameLabel.setFont(Style.CARD_FONT);
            nameLabel.setForeground(Style.DARK_GREY);

            JLabel priceLabel = new JLabel(String.format("%.2f TL", itemPrice));
            priceLabel.setFont(Style.BOLD_FONT);
            priceLabel.setForeground(Color.GRAY);

            itemRow.add(nameLabel, BorderLayout.WEST);
            itemRow.add(priceLabel, BorderLayout.EAST);

            contentPanel.add(itemRow);
            contentPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        }

        // Alt Kısım (Toplam ve Buton)
        JPanel footerPanel = new JPanel();
        footerPanel.setLayout(new BoxLayout(footerPanel, BoxLayout.Y_AXIS));
        footerPanel.setBackground(Color.WHITE);
        footerPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 30, 25));

        JSeparator sep = new JSeparator();
        sep.setForeground(Style.BORDER_COLOR);

        JLabel totalLabel = new JLabel("Toplam: " + String.format("%.2f TL", calculatedTotal));
        totalLabel.setFont(Style.PRICE_FONT);
        totalLabel.setForeground(Style.ORANGE);
        totalLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnConfirm = new JButton("Siparişi Onayla");
        Style.applyPrimaryBtn(btnConfirm);
        btnConfirm.setMaximumSize(new Dimension(Integer.MAX_VALUE, 55)); // Biraz daha büyük
        btnConfirm.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnConfirm.setCursor(new Cursor(Cursor.HAND_CURSOR));

        footerPanel.add(sep);
        footerPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        footerPanel.add(totalLabel);
        footerPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        footerPanel.add(btnConfirm);

        final double finalTotal = calculatedTotal;
        final String finalProductDetails = productNamesBuilder.toString();

        btnConfirm.addActionListener(e -> {
            if (basketItems.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Sepetiniz boş!", "Uyarı", JOptionPane.WARNING_MESSAGE);
                return;
            }
            db.createOrder(cardPay.isSelected() ? "Kart" : "Kapıda Ödeme", addrField.getText(), finalTotal, finalProductDetails);
            JOptionPane.showMessageDialog(this, "Siparişiniz başarıyla alındı!");
            frame.showPage("History");
        });

        summaryPanel.add(new JScrollPane(contentPanel), BorderLayout.CENTER);
        summaryPanel.add(footerPanel, BorderLayout.SOUTH);

        add(leftPanel, BorderLayout.CENTER);
        add(summaryPanel, BorderLayout.EAST);
    }

    // Radyo butonları için ortak stil metodu
    private void setupRadioButton(JRadioButton rb) {
        rb.setFont(Style.BOLD_FONT);
        rb.setForeground(Style.DARK_GREY);
        rb.setFocusPainted(false);
        rb.setOpaque(false);
        rb.setCursor(new Cursor(Cursor.HAND_CURSOR));
        // Hover/Seçim sırasında daha iyi görünmesi için boşluk ekledik
        rb.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }
}