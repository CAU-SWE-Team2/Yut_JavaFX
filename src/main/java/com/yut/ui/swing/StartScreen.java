package com.yut.ui.swing;

import com.yut.ui.swing.MainFrame;

import javax.swing.*;
import java.awt.*;

public class StartScreen extends JPanel {

    public StartScreen(MainFrame frame) {
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Start Screen", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        add(title, BorderLayout.CENTER);

        // 보드 종류 (4~6각형) 선택 버튼
        // 버튼 왼쪽에서 오른쪽으로 정렬
        JPanel boardPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 50));
        BoardCanvas board4 = new BoardCanvas(4);
        BoardCanvas board5 = new BoardCanvas(5);
        BoardCanvas board6 = new BoardCanvas(6);

        // 버튼 크기 설정
        Dimension buttonSize = new Dimension(250, 250); // width x height

        board4.setPreferredSize(buttonSize);
        board5.setPreferredSize(buttonSize);
        board6.setPreferredSize(buttonSize);

        // 한번에 하나만 누를 수 있게 함
        ButtonGroup group = new ButtonGroup();
        group.add(board4);
        group.add(board5);
        group.add(board6);

        // 버튼 화면에 추가
        boardPanel.add(board4);
        boardPanel.add(board5);
        boardPanel.add(board6);
        add(boardPanel, BorderLayout.NORTH);

        JPanel configPanel = new JPanel();
        configPanel.setLayout(new BoxLayout(configPanel, BoxLayout.Y_AXIS));

        // 버튼 선택시 테두리
        board4.addActionListener(e -> {
            board4.setSelectedStyle(true);
            board5.setSelectedStyle(false);
            board6.setSelectedStyle(false);
        });

        board5.addActionListener(e -> {
            board4.setSelectedStyle(false);
            board5.setSelectedStyle(true);
            board6.setSelectedStyle(false);
        });

        board6.addActionListener(e -> {
            board4.setSelectedStyle(false);
            board5.setSelectedStyle(false);
            board6.setSelectedStyle(true);
        });

        // 플레이어 수 설정
        JLabel playerLabel = new JLabel("플레이어 수:");
        playerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        configPanel.add(playerLabel);

        JSpinner playerCount = new JSpinner(new SpinnerNumberModel(2, 2, 4, 1));
        playerCount.setMaximumSize(new Dimension(100, 25)); // makes it compact
        playerCount.setAlignmentX(Component.CENTER_ALIGNMENT);
        configPanel.add(playerCount);

        configPanel.add(Box.createVerticalStrut(20)); // spacing between sections

        // Piece Count
        JLabel pieceLabel = new JLabel("말 개수:");
        pieceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        configPanel.add(pieceLabel);

        JSpinner pieceCount = new JSpinner(new SpinnerNumberModel(4, 2, 5, 1));
        pieceCount.setMaximumSize(new Dimension(100, 25)); // compact
        pieceCount.setAlignmentX(Component.CENTER_ALIGNMENT);
        configPanel.add(pieceCount);

        add(configPanel, BorderLayout.CENTER);

        // 게임 시작 버튼
        JButton startButton = new JButton("게임 시작");

        Dimension startButtonSize = new Dimension(150, 40);
        startButton.setPreferredSize(startButtonSize);

        startButton.addActionListener(e -> {
            int selectedBoardType = 4; // 기본값
            if (board5.isSelected())
                selectedBoardType = 5;
            else if (board6.isSelected())
                selectedBoardType = 6;

            int players = (Integer) playerCount.getValue();
            int pieces = (Integer) pieceCount.getValue();

            frame.showGame(selectedBoardType, players, pieces);
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
