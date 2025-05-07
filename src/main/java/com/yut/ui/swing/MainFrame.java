package com.yut.ui.swing;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public static final String START = "start";
    public static final String GAME = "game";

    private CardLayout cardLayout = new CardLayout();
    private JPanel cardPanel = new JPanel(cardLayout);

    public MainFrame() {
        setTitle("Yut Game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 675);
        setLocationRelativeTo(null);

        // Screens
        StartScreen start = new StartScreen(this);
        GameScreen game = new GameScreen(this);

        cardPanel.add(start, START);
        cardPanel.add(game, GAME);

        add(cardPanel);
        cardLayout.show(cardPanel, START);
    }

    public void showStart() {
        cardLayout.show(cardPanel, START);
    }

    public void showGame() {
        cardLayout.show(cardPanel, GAME);
    }
}
