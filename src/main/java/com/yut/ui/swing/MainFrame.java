package com.yut.ui.swing;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public static final String START = "start";
    public static final String GAME = "game";

    private CardLayout cardLayout = new CardLayout();
    private JPanel cardPanel = new JPanel(cardLayout);

    private GameScreen game;

    public GameScreen getGameScreen() {
        return game;
    }

    public MainFrame() {
        setTitle("Yut Game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 675);
        setLocationRelativeTo(null);

        // Screens
        StartScreen start = new StartScreen(this);

        cardPanel.add(start, START);

        add(cardPanel);
        cardLayout.show(cardPanel, START);
    }

    public void showStart() {
        cardLayout.show(cardPanel, START);
    }

    public void showGame(int boardType, int playerCount, int pieceCount) {
        game = new GameScreen(this, boardType, playerCount);
        cardPanel.add(game, GAME);
        cardLayout.show(cardPanel, GAME);
    }
}
