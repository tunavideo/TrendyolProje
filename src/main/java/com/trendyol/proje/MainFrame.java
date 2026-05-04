package com.trendyol.proje;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    CardLayout cardLayout = new CardLayout();
    JPanel mainPanel = new JPanel(cardLayout);
    DatabaseManager db = new DatabaseManager();
    private CartUI cartUI;
    private FavoritesUI favoritesUI;
    private OrderHistoryUI orderHistoryUI;

    public MainFrame() {
        setTitle("Trendyol - Akıllı Alışveriş");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        mainPanel.add(new UserHomeUI(this), "Home");
        cartUI = new CartUI(this);
        mainPanel.add(cartUI, "Cart");
        favoritesUI = new FavoritesUI(this);
        mainPanel.add(favoritesUI, "Favorites");
        orderHistoryUI = new OrderHistoryUI(this);
        mainPanel.add(orderHistoryUI, "History");

        add(mainPanel);
        setVisible(true);
    }

    public void showPage(String name) {
        if ("Cart".equals(name)) {
            cartUI.refresh();
        } else if ("Favorites".equals(name)) {
            favoritesUI.refresh();
        } else if ("History".equals(name)) {
            orderHistoryUI.refresh();
        }
        cardLayout.show(mainPanel, name);
    }
}