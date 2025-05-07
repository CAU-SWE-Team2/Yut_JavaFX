package com.yut.ui.swing;

import javax.swing.*;
import java.awt.*;

public class GameScreen extends JPanel {

    public GameScreen(MainFrame frame) {
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
        boardPanel.setBackground(Color.YELLOW);
        boardPanel.add(new JLabel("윷판"));

        ControlPanel controlPanel = new ControlPanel();

        centerPanel.add(boardPanel);
        centerPanel.add(controlPanel);
        add(centerPanel, BorderLayout.CENTER);

        // 3. 하단: 플레이어 상태 패널
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(new JLabel("플레이어 정보 표시"));
        add(bottomPanel, BorderLayout.SOUTH);
    }
}
