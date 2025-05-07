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

        // 버튼 클릭 시 게임 화면으로 전환
        startButton.addActionListener(e -> {
            dispose(); // 현재 창 닫기
            new GameBoardWindow(); // 게임 화면 창 열기
        });

        panel.add(startButton);
        add(panel);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameWindow::new);
    }
}
