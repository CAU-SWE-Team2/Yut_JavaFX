package com.yut.ui.swing;

import javax.swing.JComponent;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Piece extends JComponent {
    private Color color;

    public Piece(Color color) {
        this.color = color;
        setSize(20, 20); // 말의 크기
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(color);
        g2.fillOval(0, 0, getWidth(), getHeight());
        g2.setColor(Color.DARK_GRAY);
        g2.drawOval(0, 0, getWidth(), getHeight());
    }
}
