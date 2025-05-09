package com.yut.ui.swing;

import javax.swing.*;
import java.awt.*;

public class ControlPanel extends JPanel {
    //enables reference to yut in GameScreen
    private final JButton[] yutButtons = new JButton[6];
    private final JButton randomButton;
    private final JButton selectButton;

    public JButton[] getYutButtons() {
        return yutButtons;
    }

    public JButton getRandomButton() {
        return randomButton;
    }

    public JButton getSelectButton() {
        return selectButton;
    }

    //method for highlighting, called in GameScreen
    public void highlightYutButton(int yut){
        for (JButton button : yutButtons) {
            button.setBackground(null); // Reset to default
        }
        if (yut < 0) throw new IllegalArgumentException("Yut must be a positive integer for (int yut)");
        yutButtons[yut].setBackground(Color.YELLOW);
    }

    public ControlPanel() {
        setLayout(new GridLayout(3, 1)); // 위아래 두 줄

        // 1. 윷 던지기 버튼들 (한 줄)
        JPanel throwButtonsPanel = new JPanel(new FlowLayout());

        randomButton = new JButton("랜덤 던지기");
        selectButton = new JButton("지정 던지기");

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

        // 버튼 이벤트 설정

        //technically the backend's job, but kept for testing sakes
        randomButton.addActionListener(e -> {
            String[] results = { "빽도", "도", "개", "걸", "윷", "모" };
            String result = results[(int) (Math.random() * results.length)];
            JOptionPane.showMessageDialog(this, "랜덤 결과: " + result);
            for (Component c : choosePanel.getComponents()) {
                c.setEnabled(false);
            }
        });


        selectButton.addActionListener(e -> {
            // 지정 버튼을 활성화
            for (Component c : choosePanel.getComponents()) {
                c.setEnabled(true);
            }
        });

        add(throwButtonsPanel);
        add(choosePanel);
    }
}
