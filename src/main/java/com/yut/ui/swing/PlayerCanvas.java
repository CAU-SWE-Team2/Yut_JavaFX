package com.yut.ui.swing;

import javax.swing.*;
import java.awt.*;

public class PlayerCanvas extends JPanel {
    private int playerId;
    private int pieceCount;

    public PlayerCanvas(int playerId, int pieceCount) {
        this.playerId = playerId;
        this.pieceCount = pieceCount;
        setPreferredSize(new Dimension(180, 80));
        setOpaque(false);
    }

    public void setPieceCount(int count) {
        this.pieceCount = count;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 배경 직사각형
        g2.setColor(new Color(230, 230, 230));
        g2.fillRoundRect(10, 10, getWidth() - 20, getHeight() - 20, 20, 15);
        g2.setColor(Color.GRAY);
        g2.drawRoundRect(10, 10, getWidth() - 20, getHeight() - 20, 20, 15);

        // 플레이어 ID 출력
        g2.setColor(Color.BLACK);
        g2.drawString("P" + playerId, 20, 25);

        // 말 표시
        int radius = 10;
        int spacing = 5;
        int totalWidth = pieceCount * (2 * radius) + (pieceCount - 1) * spacing;
        int startX = (getWidth() - totalWidth) / 2;
        int y = 45;

        for (int i = 0; i < pieceCount; i++) {
            int x = startX + i * (2 * radius + spacing);
            g2.setColor(new Color(100, 149, 237));
            g2.fillOval(x, y, 2 * radius, 2 * radius);
            g2.setColor(Color.DARK_GRAY);
            g2.drawOval(x, y, 2 * radius, 2 * radius);
        }
    }
}
