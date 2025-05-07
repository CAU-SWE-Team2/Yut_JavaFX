package com.yut.ui.swing;

import javax.swing.*;
import java.awt.*;

public class GameScreen extends JPanel {

    public GameScreen(MainFrame frame, int boardType, int playerCount, int pieceCount) {
        setLayout(new BorderLayout());

        // 1. 상단: TopPanel (시작화면 버튼)
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton backButton = new JButton("← 시작 화면으로");
        topPanel.add(backButton);
        add(topPanel, BorderLayout.NORTH);

        // "시작 화면으로" 버튼 클릭 시 MainFrame에서 전환
        backButton.addActionListener(e -> frame.showStart());

        // 2. 중앙: 좌/우 분할
        JPanel centerPanel = new JPanel(new GridLayout(1, 2));

        JPanel boardPanel = new JPanel();
        boardPanel.setBackground(Color.CYAN);
        BoardCanvas boardCanvas = new BoardCanvas(boardType);
        boardCanvas.setPreferredSize(new Dimension(400, 400));
        boardPanel.add(boardCanvas);

        ControlPanel controlPanel = new ControlPanel();

        centerPanel.add(boardPanel);
        centerPanel.add(controlPanel);
        add(centerPanel, BorderLayout.CENTER);

        // 3. 하단: 플레이어 상태 패널
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        for (int i = 1; i <= playerCount; i++) {
            PlayerCanvas player = new PlayerCanvas(i, pieceCount); // playerId, 말 개수
            bottomPanel.add(player);
        }

        add(bottomPanel, BorderLayout.SOUTH);

    }
}
