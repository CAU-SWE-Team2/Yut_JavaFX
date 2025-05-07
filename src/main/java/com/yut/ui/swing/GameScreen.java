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
        JLayeredPane layeredBoard = new JLayeredPane();
        layeredBoard.setPreferredSize(new Dimension(400, 400));

        BoardCanvas boardCanvas = new BoardCanvas(boardType);
        boardCanvas.setBounds(0, 0, 400, 400); // 위치 고정
        layeredBoard.add(boardCanvas, JLayeredPane.DEFAULT_LAYER);

        Color[] playerColors = {
                new Color(240, 128, 128), // 빨강 (P1)
                new Color(176, 224, 230), // 파랑 (P2)
                new Color(198, 233, 105), // 초록 (P3)
                new Color(221, 160, 221) // 보라 (P4)
        };

        for (int i = 0; i < playerCount; i++) {
            Color color = playerColors[i % playerColors.length];
            Piece piece = new Piece(color);
            piece.setBounds(100 + i * 30, 100, 20, 20); // 말 위치 조정
            layeredBoard.add(piece, JLayeredPane.PALETTE_LAYER);
        }

        // 래핑해서 패널에 넣기
        JPanel boardPanel = new JPanel();
        boardPanel.add(layeredBoard);

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
