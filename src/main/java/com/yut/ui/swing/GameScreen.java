package com.yut.ui.swing;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class GameScreen extends JPanel {
    private final Map<Integer, Integer> piecesEntered = new HashMap<>(); // 각 플레이어가 말판에 올려놓은 말의 수
    private final Map<Integer, Integer> piecesFinished = new HashMap<>(); // 각 플레이어가 모두 통과시킨 말의 수
    private final Map<Integer, PlayerCanvas> playerCanvases = new HashMap<>(); // 남은 말 개수 (PlayerCanvas) 저장
    private MainFrame frame;
    private int boardType;
    private int playerCount;
    private int pieceCount;

    // 말이 말판에 올라갈 때
    public void addPieceToBoard(int playerId, int totalPieceCount) {
        int entered = piecesEntered.getOrDefault(playerId, 0);
        piecesEntered.put(playerId, entered + 1);
        updateBottom(playerId, totalPieceCount);
    }

    // 말이 완주할 때
    public void addFinishedPiece(int playerId, int totalPieceCount) {
        int entered = piecesEntered.getOrDefault(playerId, 0);
        int finished = piecesFinished.getOrDefault(playerId, 0) + 1;
        piecesFinished.put(playerId, finished);

        if (finished > entered) {
            JOptionPane.showMessageDialog(this, "말이 들어오지 않았는데 도착 처리됨 (플레이어 " + playerId + ")", "오류",
                    JOptionPane.WARNING_MESSAGE);
            piecesFinished.put(playerId, entered);
            return;
        }

        if (finished == totalPieceCount) {
            int result = JOptionPane.showOptionDialog(
                    this,
                    "플레이어 " + playerId + " 우승!",
                    "게임 종료",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    new Object[] { "게임 종료", "다시 시작" },
                    null);

            if (result == 0) {
                System.exit(0); // 게임 종료
            } else if (result == 1 && frame != null) {
                frame.showGame(boardType, playerCount, pieceCount); // 다시 시작
            }
        }
    }

    // 하단 상태창(PlayerCanvas) 갱신
    private void updateBottom(int playerId, int totalPieceCount) {
        int entered = piecesEntered.getOrDefault(playerId, 0);
        int remaining = totalPieceCount - entered;

        PlayerCanvas canvas = playerCanvases.get(playerId);
        if (canvas != null) {
            canvas.setPieceCount(remaining);
        }
    }

    public GameScreen(MainFrame frame, int boardType, int playerCount, int pieceCount) {
        this.frame = frame;
        this.boardType = boardType;
        this.playerCount = playerCount;
        this.pieceCount = pieceCount;
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton backButton = new JButton("← 시작 화면으로");
        topPanel.add(backButton);
        add(topPanel, BorderLayout.NORTH);

        backButton.addActionListener(e -> frame.showStart());

        JPanel centerPanel = new JPanel(new GridLayout(1, 2));
        JLayeredPane layeredBoard = new JLayeredPane();
        layeredBoard.setPreferredSize(new Dimension(400, 400));

        BoardCanvas boardCanvas = new BoardCanvas(boardType);

        boardCanvas.setBounds(0, 0, 400, 400); // 위치 고정
        boardCanvas.setEnabled(false);
        boardCanvas.setInteractive(true);

        layeredBoard.add(boardCanvas, JLayeredPane.DEFAULT_LAYER);


        Color[] playerColors = {
                new Color(240, 128, 128),
                new Color(176, 224, 230),
                new Color(198, 233, 105),
                new Color(221, 160, 221)
        };

        for (int i = 0; i < playerCount; i++) {
            Color color = playerColors[i % playerColors.length];
            Piece piece = new Piece(color);
            piece.setBounds(100 + i * 30, 100, 20, 20);
            layeredBoard.add(piece, JLayeredPane.PALETTE_LAYER);
        }

        JPanel boardPanel = new JPanel();
        boardPanel.add(layeredBoard);
        ControlPanel controlPanel = new ControlPanel();

        centerPanel.add(boardPanel);
        centerPanel.add(controlPanel);
        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanelWrapper = new JPanel();
        bottomPanelWrapper.setLayout(new BorderLayout());

        JLabel statusLabel = new JLabel("남은 말 개수 표시", SwingConstants.CENTER);
        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        bottomPanelWrapper.add(statusLabel, BorderLayout.NORTH);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        for (int i = 1; i <= playerCount; i++) {
            piecesEntered.put(i, 0);
            piecesFinished.put(i, 0);
            Color color = playerColors[(i - 1) % playerColors.length];
            int remaining = pieceCount;
            PlayerCanvas player = new PlayerCanvas(i, remaining, color);
            playerCanvases.put(i, player);
            bottomPanel.add(player);
        }

        bottomPanelWrapper.add(bottomPanel, BorderLayout.CENTER);
        add(bottomPanelWrapper, BorderLayout.SOUTH);

        // 테스트용: 각 플레이어에게 다양한 수의 말 진입 및 도착 처리
        addPieceToBoard(1, pieceCount);
        addPieceToBoard(1, pieceCount);
        addFinishedPiece(1, pieceCount);

        addPieceToBoard(2, pieceCount);
        addFinishedPiece(2, pieceCount);

        addPieceToBoard(3, pieceCount);
        addPieceToBoard(3, pieceCount);
        addPieceToBoard(3, pieceCount);
        addPieceToBoard(3, pieceCount);

        addFinishedPiece(3, pieceCount);
        addFinishedPiece(3, pieceCount);
        addFinishedPiece(3, pieceCount);
        addFinishedPiece(3, pieceCount);// 말이 4개 이하면 우승

    }
}
