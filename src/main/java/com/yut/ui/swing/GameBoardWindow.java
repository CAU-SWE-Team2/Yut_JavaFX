package com.yut.ui.swing;

import javax.swing.*;
import java.awt.*;

public class GameBoardWindow extends JFrame {

    public GameBoardWindow() {
        setTitle("윷놀이 - 게임 진행");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // 화면 중앙 정렬
        setVisible(true);

        // 레이아웃
        setLayout(new BorderLayout());
        // 1. 상단: TopPanel (시작화면 버튼)
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton backButton = new JButton("← 시작 화면으로");
        topPanel.add(backButton);
        add(topPanel, BorderLayout.NORTH);

        // 2. 하단: BottomPanel (플레이어 상태)
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(new JLabel("플레이어 정보 표시"));
        add(bottomPanel, BorderLayout.SOUTH);

        // 3. 중앙: 좌/우 분할
        JPanel centerPanel = new JPanel(new GridLayout(1, 2)); // 1행 2열

        JPanel boardPanel = new JPanel();
        boardPanel.setBackground(Color.YELLOW);
        boardPanel.add(new JLabel("윷판"));

        ControlPanel controlPanel = new ControlPanel(); // 기존 클래스

        centerPanel.add(boardPanel);
        centerPanel.add(controlPanel);
        add(centerPanel, BorderLayout.CENTER);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameBoardWindow::new);
    }
}
