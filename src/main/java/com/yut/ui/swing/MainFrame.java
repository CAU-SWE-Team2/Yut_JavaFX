package com.yut.ui.swing;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public static final String START = "start";
    public static final String GAME = "game";

    private CardLayout cardLayout = new CardLayout();
    private JPanel cardPanel = new JPanel(cardLayout);

    private GameScreen game;
    private StartScreen start;

    public GameScreen getGameScreen() {
        return game;
    }

    public MainFrame() {
        setTitle("Yut Game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 675);
        setLocationRelativeTo(null);

        // Screens
        this.start = new StartScreen(this);

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

    public StartScreen getStart() {
        return start;
    }   

    public GameScreen getGame() {
        return game;
    }   

    public static void main(String[] args) {
        
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
