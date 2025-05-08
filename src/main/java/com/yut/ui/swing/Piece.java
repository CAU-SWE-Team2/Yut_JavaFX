package com.yut.ui.swing;

import javax.swing.JComponent;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;

public class Piece extends JComponent {
    private Color color;

    public Piece(Color color) {
        this.color = color;
        setSize(20, 20); // 말의 크기
        setOpaque(false);
    }

    // for (int i = 0; i < pieceCount; i++) {
    // int x = startX + i * (2 * radius + spacing);
    // g2.setColor(color);
    // g2.fillOval(x, y, 2 * radius, 2 * radius);
    // g2.setColor(Color.DARK_GRAY);
    // g2.drawOval(x, y, 2 * radius, 2 * radius);
    // }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(color);
        g2.setStroke(new BasicStroke(1));
        g2.fillOval(0, 0, getWidth(), getHeight());
        g2.setColor(Color.DARK_GRAY);
        g2.drawOval(0, 0, getWidth(), getHeight());
    }
}
