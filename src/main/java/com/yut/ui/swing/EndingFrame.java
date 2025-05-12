package com.yut.ui.swing;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class EndingFrame extends JFrame {
    private int winner;
    private JButton restartButton = new JButton("Restart");
    private JButton exitButton = new JButton("Exit");

    public EndingFrame(int winner) {
        this.winner = winner;
        this.setLayout(new BorderLayout());

        ButtonPanel buttonPanel = new ButtonPanel();

        restartButton.addActionListener(e -> {
            this.setVisible(false);
        });

        JLabel winnerLabel = new JLabel("Player " + winner + " wins!", JLabel.CENTER);
        add("Center", winnerLabel);
        add("South", buttonPanel);

        setTitle("Game Over");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    class ButtonPanel extends JPanel {
        public ButtonPanel() {
            setLayout(new FlowLayout());
            add(restartButton);
            add(exitButton);
            exitButton.addActionListener(e -> {
                System.exit(0);
            });
        }
    }

    public void addRestartButtonListener(ActionListener listener) {
        restartButton.addActionListener(listener);
    }
}
