package com.trendyol.proje;

import javax.swing.*;
import java.awt.*;

public class CargoTrackingUI extends JFrame {
    String[] stages = {"Sipariş Alındı", "Hazırlanıyor", "Kargoya Verildi", "Teslim Edildi"};
    int step = 0;
    DatabaseManager db = new DatabaseManager();
    String currentOrderId; // Veritabanı işlemleri için tam 24 karakterlik ID'yi tutar

    JPanel progressPanel = new JPanel(new GridLayout(1, 4, 10, 10));

    public CargoTrackingUI(String fullOrderId, String details) {
        this.currentOrderId = fullOrderId; // MongoDB için tam ID'yi saklıyoruz

        // Ekranda şık görünmesi için sadece son 6 karakteri alıyoruz (Eğer ID tam boyutluysa)
        String displayId = fullOrderId.length() >= 24 ? fullOrderId.substring(18) : fullOrderId;

        // --- ARAYÜZ AYARLARI ---
        setTitle("Kargo Takibi #" + displayId); // Başlıkta kısa ID
        setSize(600, 400);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);
        setLayout(new BorderLayout(20, 20));

        JLabel header = new JLabel("Sipariş Durumu", JLabel.CENTER);
        header.setFont(Style.TITLE_FONT);
        header.setForeground(Style.ORANGE);
        add(header, BorderLayout.NORTH);

        renderStages(step);

        JPanel infoBox = new JPanel(new GridLayout(2, 1));
        infoBox.setBackground(Style.BG_LIGHT);
        infoBox.setBorder(Style.CARD_BORDER);
        infoBox.add(new JLabel("Takip No: " + displayId, JLabel.CENTER)); // Ekranda kısa ID
        infoBox.add(new JLabel("İçerik: " + details, JLabel.CENTER));

        add(progressPanel, BorderLayout.CENTER);
        add(infoBox, BorderLayout.SOUTH);

        setVisible(true);
        startAutoUpdate();
    }

    private void renderStages(int currentStep) {
        progressPanel.removeAll();
        for (int i = 0; i < stages.length; i++) {
            JPanel stageCard = new JPanel(new BorderLayout());
            stageCard.setBackground(i <= currentStep ? new Color(255, 245, 230) : Color.WHITE);
            stageCard.setBorder(BorderFactory.createLineBorder(i <= currentStep ? Style.ORANGE : Style.BORDER_COLOR));

            JLabel lbl = new JLabel(stages[i], JLabel.CENTER);
            lbl.setFont(i == currentStep ? Style.BOLD_FONT : Style.CARD_FONT);
            lbl.setForeground(i <= currentStep ? Style.ORANGE : Color.GRAY);

            stageCard.add(lbl, BorderLayout.CENTER);
            progressPanel.add(stageCard);
        }
        progressPanel.revalidate();
        progressPanel.repaint();
    }

    private void startAutoUpdate() {
        Timer timer = new Timer(15000, e -> {
            if (step < stages.length - 1) {
                step++;
                String newStatus = stages[step];

                renderStages(step);

                new SwingWorker<Void, Void>() {
                    @Override
                    protected Void doInBackground() {
                        db.updateSpecificOrderStatus(currentOrderId, newStatus);
                        return null;
                    }
                }.execute();

            } else {
                ((Timer)e.getSource()).stop();
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(null, "Bu siparişiniz teslim edilmiştir!");
                });
            }
        });

        timer.setInitialDelay(2000);
        timer.start();
    }
}