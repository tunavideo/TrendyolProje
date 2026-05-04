package com.trendyol.proje;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class Style {
    public static final Color ORANGE = new Color(255, 111, 0); // Marka Turuncusu
    public static final Color ORANGE_HOVER = new Color(255, 140, 0); // Üzerine gelince
    public static final Color BG_LIGHT = new Color(248, 248, 248); // Sayfa Arka Planı
    public static final Color BORDER_COLOR = new Color(230, 230, 230); // Normal Kenarlık
    public static final Color DARK_GREY = new Color(51, 51, 51);

    public static final Font TITLE_FONT = new Font("Segoe UI Semibold", Font.BOLD, 28);
    public static final Font CARD_TITLE_FONT = new Font("Segoe UI Semibold", Font.PLAIN, 18);
    public static final Font CARD_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font PRICE_FONT = new Font("Segoe UI Bold", Font.BOLD, 18);
    public static final Font BOLD_FONT = new Font("Segoe UI Bold", Font.BOLD, 14);

    public static final Border CARD_BORDER = BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
    );

    // 1. Modern Buton Efekti (CSS :hover)
    public static void applyPrimaryBtn(JButton btn) {
        btn.setBackground(ORANGE);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(BOLD_FONT);
        btn.setBorder(new EmptyBorder(12, 25, 12, 25));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(ORANGE_HOVER); // Rengi aç
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(ORANGE); // Rengi geri al
            }
        });
    }

    public static void applyCardStyle(JPanel panel) {
        panel.setBackground(Color.WHITE);
        panel.setBorder(CARD_BORDER);

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                panel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(255, 190, 150), 2),
                        BorderFactory.createEmptyBorder(19, 19, 19, 19) // Paddingi koru
                ));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                panel.setBorder(CARD_BORDER);
            }
        });
    }
}