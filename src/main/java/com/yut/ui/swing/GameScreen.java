package com.yut.ui.swing;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class GameScreen extends JPanel {
    private final Map<Integer, Integer> piecesEntered = new HashMap<>();
    private final Map<Integer, PlayerCanvas> playerCanvases = new HashMap<>();

    public void addPieceToBoard(int playerId, int totalPieceCount) {
        int entered = piecesEntered.getOrDefault(playerId, 0);
        piecesEntered.put(playerId, entered + 1);
        updateBottom(playerId, totalPieceCount);
    }

    private void updateBottom(int playerId, int totalPieceCount) {
        int entered = piecesEntered.getOrDefault(playerId, 0);
        int remaining = totalPieceCount - entered;

        PlayerCanvas canvas = playerCanvases.get(playerId);
        if (canvas != null) {
            canvas.setPieceCount(remaining);
        }
    }

    public GameScreen(MainFrame frame, int boardType, int playerCount, int pieceCount) {
        setLayout(new BorderLayout());

        // 1. 상단 패널 - 시작 화면으로
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton backButton = new JButton("← 시작 화면으로");
        topPanel.add(backButton);
        add(topPanel, BorderLayout.NORTH);

        backButton.addActionListener(e -> frame.showStart());

        // 2. 중앙 패널 - 말판과 컨트롤
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

        // 버튼 부분
        JPanel boardPanel = new JPanel();
        boardPanel.add(layeredBoard);
        ControlPanel controlPanel = new ControlPanel();

        centerPanel.add(boardPanel);
        centerPanel.add(controlPanel);
        add(centerPanel, BorderLayout.CENTER);

        // 3. 하단 패널 - 플레이어 상태
        JPanel bottomPanelWrapper = new JPanel();
        bottomPanelWrapper.setLayout(new BorderLayout());

        JLabel statusLabel = new JLabel("남은 말 개수 표시", SwingConstants.CENTER);
        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        bottomPanelWrapper.add(statusLabel, BorderLayout.NORTH);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        for (int i = 1; i <= playerCount; i++) {
            piecesEntered.put(i, 0);
            Color color = playerColors[(i - 1) % playerColors.length];
            int used = piecesEntered.getOrDefault(i, 0); // 올라간 말 수
            int remaining = pieceCount; // 남은 말 수
            PlayerCanvas player = new PlayerCanvas(i, remaining, color);
            playerCanvases.put(i, player);

            bottomPanel.add(player);
        }

        bottomPanelWrapper.add(bottomPanel, BorderLayout.CENTER);
        add(bottomPanelWrapper, BorderLayout.SOUTH);

        // 테스트용: 각 플레이어에게 다양한 수의 말 진입 처리
        addPieceToBoard(1, pieceCount); // P1 - 1개
        addPieceToBoard(1, pieceCount); // P1 - 2개
        addPieceToBoard(2, pieceCount); // P2 - 1개
        addPieceToBoard(3, pieceCount); // P3 - 1개
        addPieceToBoard(3, pieceCount); // P3 - 2개
        addPieceToBoard(3, pieceCount); // P3 - 3개

    }
}
