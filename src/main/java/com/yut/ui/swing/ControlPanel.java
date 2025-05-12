package com.yut.ui.swing;

import javax.swing.*;
import java.awt.*;

public class ControlPanel extends JPanel {
    // enables reference to yut in GameScreen
    private final JButton[] yutButtons = new JButton[6];
    private final JButton randomButton;
    private final JButton selectButton;
    private final JButton moveNewPieceButton;
    private final JButton goalButton;

    public JButton[] getYutButtons() {
        return yutButtons;
    }

    public JButton getRandomButton() {
        return randomButton;
    }

    public JButton getSelectButton() {
        return selectButton;
    }

    public JButton getMoveNewPieceButton() {
        return moveNewPieceButton;
    }

    public JButton getGoalButton() {
        return goalButton;
    }
    // method for highlighting, called in GameScreen
    public void highlightYutButton(int yut) {
        if (yut < 0 || yut >= yutButtons.length)
            throw new IllegalArgumentException("yut index out of range: " + yut);

        for (JButton b : yutButtons) {
            b.setBackground(null); // clear any old colour
            b.setEnabled(false); // reset to original disabled state
            b.setOpaque(true); // make sure bg can be painted
        }

        // 🔸 highlight the chosen one
        JButton highlight = yutButtons[yut];
        highlight.setEnabled(true); // allow normal painting
        highlight.setBackground(Color.YELLOW);
        highlight.repaint();
    }

    public ControlPanel() {
        setLayout(new GridLayout(3, 1)); // 위아래 두 줄

        // 1. 윷 던지기 버튼들 (한 줄)
        JPanel throwButtonsPanel = new JPanel(new FlowLayout());
        throwButtonsPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0)); // 위쪽 30px

        randomButton = new JButton("랜덤 던지기");
        randomButton.setPreferredSize(new Dimension(150, 40));
        selectButton = new JButton("지정 던지기");
        selectButton.setPreferredSize(new Dimension(150, 40));
        throwButtonsPanel.add(randomButton);
        throwButtonsPanel.add(selectButton);

        // 2. 윷 결과 선택 버튼들 (도, 개, 걸, 윷, 모, 빽도)
        JPanel choosePanel = new JPanel(new GridLayout(1, 6)); // 버튼 6개 가로배치
        String[] yutResults = { "빽도", "도", "개", "걸", "윷", "모" };

        for (int i = 0; i < yutResults.length; i++) {
            JButton yutButton = new JButton(yutResults[i]);
            yutButton.setEnabled(false); // 초기에는 비활성화
            choosePanel.add(yutButton);
            yutButtons[i] = yutButton;

            // 여기에: 선택된 윷 결과를 실제 로직에 반영하는 코드 추가할 것것\
        }

        // 3. 새로운 말 움직이기 버튼 (하단)
        JPanel bottomButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomButtonPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0)); // 위쪽 30px
        moveNewPieceButton = new JButton("새로운 말 움직이기"); // or any label you want
        moveNewPieceButton.setPreferredSize(new Dimension(150, 40));

        goalButton = new JButton("골인!"); // or any label you want
        goalButton.setPreferredSize(new Dimension(150, 40));
        bottomButtonPanel.add(goalButton);
        bottomButtonPanel.add(moveNewPieceButton);

        goalButton.setVisible(false);

        // 버튼 이벤트 설정

        // technically the backend's job, but kept for testing sakes
        // randomButton.addActionListener(e -> {
        // String[] results = { "빽도", "도", "개", "걸", "윷", "모" };
        // String result = results[(int) (Math.random() * results.length)];
        // JOptionPane.showMessageDialog(this, "랜덤 결과: " + result);
        // for (Component c : choosePanel.getComponents()) {
        // c.setEnabled(false);
        // }
        // });

        selectButton.addActionListener(e -> {
            // 지정 버튼을 활성화
            for (Component c : choosePanel.getComponents()) {
                c.setEnabled(true);
            }
        });

        add(throwButtonsPanel);
        add(choosePanel);
        add(bottomButtonPanel);
    }
}
