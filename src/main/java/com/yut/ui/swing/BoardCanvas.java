package com.yut.ui.swing;

import javax.swing.*;
import java.awt.*;

public class BoardCanvas extends JToggleButton {
    private int boardType;

    public BoardCanvas(int boardType) {
        this.boardType = boardType;
        setPreferredSize(new Dimension(250, 250));
        setFocusPainted(false);
        setContentAreaFilled(false);
    }

    public int getBoardType() {
        return boardType;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        int padding = 20;
        int boardSize = Math.min(w, h) - 2 * padding;
        if (boardType == 4) {
            int step = boardSize / 4;
            int cStep = boardSize / 5; // cardinal steps
            int dStep = boardSize / 6; // diagonal steps

            int startX = padding;
            int startY = padding;

            // Draw outer square
            g2.setColor(Color.BLACK);
            g2.drawRect(startX, startY, boardSize, boardSize);

            // Draw diagonals
            g2.drawLine(startX, startY, startX + 4 * step, startY + 4 * step);
            g2.drawLine(startX, startY + 4 * step, startX + 4 * step, startY);

            // Draw edge nodes
            for (int row = 0; row <= 5; row++) {
                for (int col = 0; col <= 5; col++) {
                    boolean shouldDraw = row == 0 || row == 5 || col == 0 || col == 5;
                    boolean corner = (row == 0 || row == 5) && (col == 0 || col == 5);

                    if (shouldDraw) {
                        int cx = startX + col * cStep;
                        int cy = startY + row * cStep;
                        if (corner) {
                            drawCircle(g2, cx, cy, 16);
                            drawCircle(g2, cx, cy, 12);
                        } else
                            drawCircle(g2, cx, cy, 9);
                    }
                }
            }

            // Draw diagonal nodes

            for (int row = 0; row <= 6; row++) {
                for (int col = 0; col <= 6; col++) {
                    boolean shouldDraw = (row == col || row + col == 6) && (row != 0 && row != 3 && row != 6);

                    if (shouldDraw) {
                        int cx = startX + col * dStep;
                        int cy = startY + row * dStep;
                        drawCircle(g2, cx, cy, 9);
                    }
                }
            }

            // Draw central circle bigger
            drawCircle(g2, startX + 3 * dStep, startY + 3 * dStep, 16);
            drawCircle(g2, startX + 3 * dStep, startY + 3 * dStep, 12);

            // Draw "출발" in bottom-right node
            int textX = startX + 5 * cStep;
            int textY = startY + 5 * cStep;
            drawCenteredString(g2, "출발", textX, textY);
        }

        else if (boardType == 5) {
            int cx = w / 2;
            int cy = h / 2 + padding / 2;
            int r = Math.min(w, h) / 2 - padding; // radius from center to vertex

            // Calculate 5 pentagon points
            int[] xPoints = new int[5];
            int[] yPoints = new int[5];
            for (int i = 0; i < 5; i++) {
                double angle = Math.toRadians(-90 - 72 - i * 72); // start at top (-90°)
                xPoints[i] = cx + (int) (r * Math.cos(angle));
                yPoints[i] = cy + (int) (r * Math.sin(angle));
            }

            // Draw outer pentagon
            g2.setColor(Color.BLACK);
            g2.drawPolygon(xPoints, yPoints, 5);

            // Draw lines from each corner to center
            for (int i = 0; i < 5; i++) {
                g2.drawLine(cx, cy, xPoints[i], yPoints[i]);
            }

            // Draw circles at corners
            for (int i = 0; i < 5; i++) {
                drawCircle(g2, xPoints[i], yPoints[i], 16);
                drawCircle(g2, xPoints[i], yPoints[i], 12);
            }

            // values are all set to be positive
            int intervalTopX = (xPoints[4] - xPoints[0]) / 5;
            int intervalTopY = (yPoints[4] - yPoints[0]) / 5;
            int intervalMidX = (xPoints[3] - xPoints[2]) / 5;
            int intervalMidY = (yPoints[3] - yPoints[2]) / 5;
            int intervalBotX = (xPoints[2] - xPoints[1]) / 5;

            for (int i = 1; i < 5; i++) {
                drawCircle(g2, xPoints[0] + intervalMidX * i, yPoints[0] - intervalMidY * i, 7);
                drawCircle(g2, xPoints[1] + intervalBotX * i, yPoints[1], 7);
                drawCircle(g2, xPoints[2] + intervalMidX * i, yPoints[2] + intervalMidY * i, 7);
                drawCircle(g2, xPoints[3] - intervalTopX * i, yPoints[3] + intervalTopY * i, 7);
                drawCircle(g2, xPoints[4] - intervalTopX * i, yPoints[4] - intervalTopY * i, 7);
            }

            int intervalCTopY = (yPoints[4] - cx) / 3;
            int intervalCMidX = (xPoints[3] - cx) / 3;
            int intervalCMidY = (yPoints[3] - cy) / 3;
            int intervalCBotX = (cx - xPoints[1]) / 3;
            int intervalCBotY = (cy - yPoints[1]) / 3;

            for (int i = 1; i < 3; i++) {
                drawCircle(g2, cx - intervalCMidX * i, cy + intervalCMidY * i, 7);
                drawCircle(g2, cx - intervalCBotX * i, cy - intervalCBotY * i, 7);
                drawCircle(g2, cx + intervalCBotX * i, cy - intervalCBotY * i, 7);
                drawCircle(g2, cx + intervalCMidX * i, cy + intervalCMidY * i, 7);
                drawCircle(g2, cx, cy + intervalCTopY * i, 7);
            }

            // Draw center node
            drawCircle(g2, cx, cy, 16);
            drawCircle(g2, cx, cy, 12);

            // Optionally: Draw "출발" at bottom-right point
            int startIdx = 0; // You can change this to any corner (0~4)
            drawCenteredString(g2, "출발", xPoints[startIdx], yPoints[startIdx]);
        }

        else if (boardType == 6) {
            int cx = w / 2;
            int cy = h / 2;
            int r = Math.min(w, h) / 2 - padding; // radius from center to vertex

            // Calculate 5 pentagon points
            int[] xPoints = new int[6];
            int[] yPoints = new int[6];
            for (int i = 0; i < 6; i++) {
                double angle = Math.toRadians(-90 - 90 - i * 60); // start at top (-90°)
                xPoints[i] = cx + (int) (r * Math.cos(angle));
                yPoints[i] = cy + (int) (r * Math.sin(angle));
            }

            // Draw outer pentagon
            g2.setColor(Color.BLACK);
            g2.drawPolygon(xPoints, yPoints, 6);

            // Draw lines from each corner to center
            for (int i = 0; i < 6; i++) {
                g2.drawLine(cx, cy, xPoints[i], yPoints[i]);
            }

            // Draw circles at corners
            for (int i = 0; i < 6; i++) {
                drawCircle(g2, xPoints[i], yPoints[i], 15);
                drawCircle(g2, xPoints[i], yPoints[i], 12);
            }

            // values are all set to be positive
            int interval1X = (xPoints[3] - xPoints[2]) / 5;
            int interval1Y = (yPoints[3] - yPoints[2]) / 5;
            int interval2X = (xPoints[2] - xPoints[1]) / 5;

            for (int i = 1; i < 5; i++) {
                drawCircle(g2, xPoints[0] + interval1X * i, yPoints[0] - interval1Y * i, 6);
                drawCircle(g2, xPoints[1] + interval2X * i, yPoints[1], 6);
                drawCircle(g2, xPoints[2] + interval1X * i, yPoints[2] + interval1Y * i, 6);
                drawCircle(g2, xPoints[3] - interval1X * i, yPoints[3] + interval1Y * i, 6);
                drawCircle(g2, xPoints[4] - interval2X * i, yPoints[4], 6);
                drawCircle(g2, xPoints[5] - interval1X * i, yPoints[5] - interval1Y * i, 6);
            }

            int intervalC1X = (cx - xPoints[1]) / 3;
            int intervalC1Y = (cy - yPoints[1]) / 3;
            int intervalC2X = (cx - xPoints[0]) / 3;

            for (int i = 1; i < 3; i++) {
                drawCircle(g2, cx - intervalC2X * i, cy, 7);
                drawCircle(g2, cx - intervalC1X * i, cy + intervalC1Y * i, 7);
                drawCircle(g2, cx + intervalC1X * i, cy + intervalC1Y * i, 7);
                drawCircle(g2, cx + intervalC2X * i, cy, 7);
                drawCircle(g2, cx + intervalC1X * i, cy - intervalC1Y * i, 7);
                drawCircle(g2, cx - intervalC1X * i, cy - intervalC1Y * i, 7);
            }

            // Draw center node
            drawCircle(g2, cx, cy, 15);
            drawCircle(g2, cx, cy, 12);

            // Optionally: Draw "출발" at bottom-right point
            int startIdx = 0; // You can change this to any corner (0~4)
            drawCenteredString(g2, "출발", xPoints[startIdx], yPoints[startIdx]);
        }

    }

    private void drawCircle(Graphics2D g2, int x, int y, int r) {
        g2.setColor(Color.WHITE); // Fill color
        g2.fillOval(x - r, y - r, 2 * r, 2 * r);

        g2.setColor(Color.BLACK); // Outline
        g2.drawOval(x - r, y - r, 2 * r, 2 * r);
    }

    private void drawCenteredString(Graphics2D g2, String text, int x, int y) {
        FontMetrics fm = g2.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getAscent();
        int textX = x - textWidth / 2;
        int textY = y + textHeight / 2 - 2;
        g2.drawString(text, textX, textY);
    }

}
