package com.yut.ui.swing;

import javax.swing.*;

public class GameWindow extends JFrame {
    public GameWindow() {
        setTitle("윷놀이 게임");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // 화면 중앙 정렬

        // 패널 추가
        JPanel panel = new JPanel();
        JButton startButton = new JButton("게임 시작");

        panel.add(startButton);
        add(panel);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameWindow::new);
    }
}
