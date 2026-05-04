package com.trendyol.proje;

import javax.swing.*;
import java.awt.*;

public class LoginUI extends JFrame {
    public LoginUI() {
        setTitle("Sisteme Giriş");
        setSize(400, 300); // İçeriğin nefes alması için biraz büyütüldü
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Style.BG_LIGHT); // Arka plan rengi

        // --- BAŞLIK ALANI ---
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Style.BG_LIGHT);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 20, 0)); // Üstten ve alttan boşluk

        JLabel titleLabel = new JLabel("Hoş Geldiniz", JLabel.CENTER);
        titleLabel.setFont(Style.TITLE_FONT);
        titleLabel.setForeground(Style.DARK_GREY);
        headerPanel.add(titleLabel);

        add(headerPanel, BorderLayout.NORTH);

        // --- BUTONLAR ALANI ---
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 1, 0, 15)); // Butonlar arası 15px dikey boşluk
        buttonPanel.setBackground(Style.BG_LIGHT);
        // Yanlardan 50px boşluk bırakarak butonları ortaladık
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 40, 50));

        // 1. Müşteri Girişi (Ana Eylem - Tam Turuncu)
        JButton btnUser = new JButton("MÜŞTERİ GİRİŞİ");
        Style.applyPrimaryBtn(btnUser);

        // 2. Yönetici Girişi (İkincil Eylem - Beyaz Arkaplan, Turuncu Çizgi)
        JButton btnAdmin = new JButton("YÖNETİCİ GİRİŞİ");
        btnAdmin.setBackground(Color.WHITE);
        btnAdmin.setForeground(Style.ORANGE);
        btnAdmin.setFont(Style.BOLD_FONT);
        btnAdmin.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Style.ORANGE, 2),
                BorderFactory.createEmptyBorder(12, 25, 12, 25)
        ));
        btnAdmin.setFocusPainted(false);
        btnAdmin.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // --- TIKLAMA İŞLEMLERİ ---
        btnUser.addActionListener(e -> {
            new MainFrame();
            this.dispose();
        });

        btnAdmin.addActionListener(e -> {
            new AdminUI().setVisible(true);
            this.dispose();
        });

        // Butonları panele ekle
        buttonPanel.add(btnUser);
        buttonPanel.add(btnAdmin);

        add(buttonPanel, BorderLayout.CENTER);
    }
}