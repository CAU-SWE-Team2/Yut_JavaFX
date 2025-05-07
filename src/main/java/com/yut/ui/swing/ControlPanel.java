package com.yut.ui.swing;

import javax.swing.*;
import java.awt.*;

public class ControlPanel extends JPanel {

    public ControlPanel() {
        setLayout(new FlowLayout());

        JButton throwButton = new JButton("윷 던지기");
        throwButton.addActionListener(e -> {
            String[] results = { "빽도", "도", "개", "걸", "윷", "모" };
            int i = (int) (Math.random() * results.length);
            JOptionPane.showMessageDialog(this, "결과: " + results[i]);
        });

        add(throwButton);
    }
}
