package com.yut.ui.javaFX;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import com.yut.ui.javaFX.ClickableNodeFX;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BoardCanvasFX extends Region {
    private int boardType;
    private int size;
    private List<ClickableNodeFX> clickableNodes = new ArrayList<>();

    public ClickableNodeFX getNodeById(int id) {
        for (ClickableNodeFX node : clickableNodes) {
            if (node.getNodeID() == id)
                return node;
        }
        return null;
    }

    private int startX;
    private int startY;
    private boolean interactive = false;
    private Canvas canvas;

    public BoardCanvasFX(int boardType, int size) {
        this.boardType = boardType;
        this.size = size;

        this.setPrefSize(size, size);
        canvas = new Canvas(size, size);
        this.getChildren().add(canvas);
        drawBoard();
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public void setInteractive(boolean interactive) {
        this.interactive = interactive;
    }

    public boolean getInteractive() {
        return interactive;
    }

    public List<ClickableNodeFX> getClickableNodes() {
        return clickableNodes;
    }

    public int getBoardType() {
        return boardType;
    }

    private void drawBoard() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        int w = (int) canvas.getWidth();
        int h = (int) canvas.getHeight();
        int padding = 20;
        int boardSize = Math.min(w, h) - 2 * padding;

        if (boardType == 4) {
            int step = boardSize / 4;
            int cStep = boardSize / 5; // cardinal step
            int dStep = boardSize / 6; // diagonal step

            startX = padding;
            startY = padding;

            gc.setStroke(Color.BLACK);
            gc.setLineWidth(2);

            gc.strokeRect(startX, startY, boardSize, boardSize);
            gc.strokeLine(startX, startY, startX + boardSize, startY + boardSize);
            gc.strokeLine(startX, startY + boardSize, startX + boardSize, startY);

            // Top-right to bottom-right (right edge)
            for (int i = 0; i < 5; i++) {
                int x = startX + 5 * cStep;
                int y = startY + (5 - i) * cStep;
                int radius = (i == 0) ? 16 : 9;
                int id = (i == 0) ? 100 : 120 + i;
                ClickableNodeFX node = new ClickableNodeFX(x, y, radius, id);
                clickableNodes.add(node);
                this.getChildren().add(node);
                drawCircle(gc, x, y, radius);
                if (i == 0)
                    drawCircle(gc, x, y, 12);
            }

            // Top-right to top-left (top edge)
            for (int i = 0; i < 5; i++) {
                int x = startX + (5 - i) * cStep;
                int y = startY;
                int radius = (i == 0) ? 16 : 9;
                int id = (i == 0) ? 200 : 230 + i;
                ClickableNodeFX node = new ClickableNodeFX(x, y, radius, id);
                clickableNodes.add(node);
                this.getChildren().add(node);
                drawCircle(gc, x, y, radius);
                if (i == 0)
                    drawCircle(gc, x, y, 12);
            }

            // Top-left to bottom-left (left edge)
            for (int i = 0; i < 5; i++) {
                int x = startX;
                int y = startY + i * cStep;
                int radius = (i == 0) ? 16 : 9;
                int id = (i == 0) ? 300 : 340 + i;
                ClickableNodeFX node = new ClickableNodeFX(x, y, radius, id);
                clickableNodes.add(node);
                this.getChildren().add(node);
                drawCircle(gc, x, y, radius);
                if (i == 0)
                    drawCircle(gc, x, y, 12);
            }

            // Bottom-left to bottom-right (bottom edge)
            for (int i = 0; i < 5; i++) {
                int x = startX + i * cStep;
                int y = startY + 5 * cStep;
                int radius = (i == 0) ? 16 : 9;
                int id = (i == 0) ? 400 : 410 + i;
                ClickableNodeFX node = new ClickableNodeFX(x, y, radius, id);
                clickableNodes.add(node);
                this.getChildren().add(node);
                drawCircle(gc, x, y, radius);
                if (i == 0)
                    drawCircle(gc, x, y, 12);
            }

            // Diagonal paths
            for (int row = 1; row <= 5; row++) {
                for (int col = 1; col <= 5; col++) {
                    boolean diagTLtoC = (row == col) && (row < 3);
                    boolean diagTRtoC = (row + col == 6) && (row < 3);
                    boolean diagCtoBR = (row == col) && (row > 3);
                    boolean diagCtoBL = (row + col == 6) && (row > 3);

                    int x = startX + col * dStep;
                    int y = startY + row * dStep;

                    if (diagTLtoC) {
                        ClickableNodeFX node = new ClickableNodeFX(x, y, 9, 350 + row);
                        clickableNodes.add(node);
                        this.getChildren().add(node);
                        drawCircle(gc, x, y, 9);
                    } else if (diagTRtoC) {
                        ClickableNodeFX node = new ClickableNodeFX(x, y, 9, 250 + row);
                        clickableNodes.add(node);
                        this.getChildren().add(node);
                        drawCircle(gc, x, y, 9);
                    } else if (diagCtoBR) {
                        ClickableNodeFX node = new ClickableNodeFX(x, y, 9, 510 + row - 3);
                        clickableNodes.add(node);
                        this.getChildren().add(node);
                        drawCircle(gc, x, y, 9);
                    } else if (diagCtoBL) {
                        ClickableNodeFX node = new ClickableNodeFX(x, y, 9, 540 + row - 3);
                        clickableNodes.add(node);
                        this.getChildren().add(node);
                        drawCircle(gc, x, y, 9);
                    }
                }
            }

            // Center node
            int centerX = startX + 3 * dStep;
            int centerY = startY + 3 * dStep;
            ClickableNodeFX node = new ClickableNodeFX(centerX, centerY, 16, 500);
            clickableNodes.add(node);
            this.getChildren().add(node);
            drawCircle(gc, centerX, centerY, 16);
            drawCircle(gc, centerX, centerY, 12);
            drawText(gc, "출발", startX + 5 * cStep, startY + 5 * cStep); // label the starting point
        }

        else if (boardType == 5) {
            int cx = w / 2;
            int cy = h / 2 + padding / 2;
            int r = Math.min(w, h) / 2 - padding;

            int[] xPoints = new int[5];
            int[] yPoints = new int[5];
            for (int i = 0; i < 5; i++) {
                double angle = Math.toRadians(-90 - 72 - i * 72);
                xPoints[i] = cx + (int) (r * Math.cos(angle));
                yPoints[i] = cy + (int) (r * Math.sin(angle));
            }

            startX = xPoints[0];
            startY = yPoints[0];

            // Draw outer pentagon
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(2);
            gc.strokePolygon(Arrays.stream(xPoints).asDoubleStream().toArray(),
                    Arrays.stream(yPoints).asDoubleStream().toArray(),
                    5);

            for (int i = 0; i < 5; i++) {
                gc.strokeLine(cx, cy, xPoints[i], yPoints[i]);
            }

            // Draw circles at corners
            for (int i = 0; i < 5; i++) {
                ClickableNodeFX node = new ClickableNodeFX(xPoints[i], yPoints[i], 16, 100 * (i + 1));
                clickableNodes.add(node);
                this.getChildren().add(node);
                drawCircle(gc, xPoints[i], yPoints[i], 16);
                drawCircle(gc, xPoints[i], yPoints[i], 12);
            }

            // Intervals (to add intermediate points)
            int intervalTopX = (xPoints[4] - xPoints[0]) / 5;
            int intervalTopY = (yPoints[4] - yPoints[0]) / 5;
            int intervalMidX = (xPoints[3] - xPoints[2]) / 5;
            int intervalMidY = (yPoints[3] - yPoints[2]) / 5;
            int intervalBotX = (xPoints[2] - xPoints[1]) / 5;

            for (int i = 1; i < 5; i++) {
                int x1 = xPoints[0] + intervalMidX * i;
                int y1 = yPoints[0] - intervalMidY * i;
                ClickableNodeFX node1 = new ClickableNodeFX(x1, y1, 9, 120 + i);
                clickableNodes.add(node1);
                this.getChildren().add(node1);
                drawCircle(gc, x1, y1, 9);

                int x2 = xPoints[1] + intervalBotX * i;
                int y2 = yPoints[1];
                ClickableNodeFX node2 = new ClickableNodeFX(x2, y2, 9, 230 + i);
                clickableNodes.add(node2);
                this.getChildren().add(node2);
                drawCircle(gc, x2, y2, 9);

                int x3 = xPoints[2] + intervalMidX * i;
                int y3 = yPoints[2] + intervalMidY * i;
                ClickableNodeFX node3 = new ClickableNodeFX(x3, y3, 9, 340 + i);
                clickableNodes.add(node3);
                this.getChildren().add(node3);
                drawCircle(gc, x3, y3, 9);

                int x4 = xPoints[3] - intervalTopX * i;
                int y4 = yPoints[3] + intervalTopY * i;
                ClickableNodeFX node4 = new ClickableNodeFX(x4, y4, 9, 450 + i);
                clickableNodes.add(node4);
                this.getChildren().add(node4);
                drawCircle(gc, x4, y4, 9);

                int x5 = xPoints[4] - intervalTopX * i;
                int y5 = yPoints[4] - intervalTopY * i;
                ClickableNodeFX node5 = new ClickableNodeFX(x5, y5, 9, 510 + i);
                clickableNodes.add(node5);
                this.getChildren().add(node5);
                drawCircle(gc, x5, y5, 9);
            }

            // Inner diagonals
            int intervalCTopY = (cy - yPoints[4]) / 3;
            int intervalCMidX = (xPoints[3] - cx) / 3;
            int intervalCMidY = (yPoints[3] - cy) / 3;
            int intervalCBotX = (cx - xPoints[1]) / 3;
            int intervalCBotY = (cy - yPoints[1]) / 3;

            for (int i = 1; i < 3; i++) {
                int m1x = cx - intervalCMidX * i;
                int m1y = cy + intervalCMidY * i;
                ClickableNodeFX node1 = new ClickableNodeFX(m1x, m1y, 9, 610 + i);
                clickableNodes.add(node1);
                this.getChildren().add(node1);
                drawCircle(gc, m1x, m1y, 9);

                int m2x = xPoints[1] + intervalCBotX * i;
                int m2y = yPoints[1] + intervalCBotY * i;
                ClickableNodeFX node2 = new ClickableNodeFX(m2x, m2y, 9, 260 + i);
                clickableNodes.add(node2);
                this.getChildren().add(node2);
                drawCircle(gc, m2x, m2y, 9);

                int m3x = xPoints[2] - intervalCBotX * i;
                int m3y = yPoints[2] + intervalCBotY * i;
                ClickableNodeFX node3 = new ClickableNodeFX(m3x, m3y, 9, 360 + i);
                clickableNodes.add(node3);
                this.getChildren().add(node3);
                drawCircle(gc, m3x, m3y, 9);

                int m4x = xPoints[3] - intervalCMidX * i;
                int m4y = yPoints[3] - intervalCMidY * i;
                ClickableNodeFX node4 = new ClickableNodeFX(m4x, m4y, 9, 460 + i);
                clickableNodes.add(node4);
                this.getChildren().add(node4);
                drawCircle(gc, m4x, m4y, 9);

                int m5x = cx;
                int m5y = cy - intervalCTopY * i;
                ClickableNodeFX node5 = new ClickableNodeFX(m5x, m5y, 9, 650 + i);
                clickableNodes.add(node5);
                this.getChildren().add(node5);
                drawCircle(gc, m5x, m5y, 9);
            }

            // Center
            ClickableNodeFX node = new ClickableNodeFX(cx, cy, 16, 600);
            clickableNodes.add(node);
            this.getChildren().add(node);
            drawCircle(gc, cx, cy, 16);
            drawCircle(gc, cx, cy, 12);
            drawText(gc, "출발", xPoints[0], yPoints[0]);
        }

        else if (boardType == 6) {
            int cx = w / 2;
            int cy = h / 2;
            int r = Math.min(w, h) / 2 - padding;

            int[] xPoints = new int[6];
            int[] yPoints = new int[6];
            for (int i = 0; i < 6; i++) {
                double angle = Math.toRadians(-90 - 90 - i * 60); // Start at top
                xPoints[i] = cx + (int) (r * Math.cos(angle));
                yPoints[i] = cy + (int) (r * Math.sin(angle));
            }

            startX = xPoints[0];
            startY = yPoints[0];

            gc.setStroke(Color.BLACK);
            gc.setLineWidth(2);
            gc.strokePolygon(Arrays.stream(xPoints).asDoubleStream().toArray(),
                    Arrays.stream(yPoints).asDoubleStream().toArray(),
                    6);

            for (int i = 0; i < 6; i++) {
                gc.strokeLine(cx, cy, xPoints[i], yPoints[i]);
            }

            // Draw corner nodes
            for (int i = 0; i < 6; i++) {
                ClickableNodeFX node = new ClickableNodeFX(xPoints[i], yPoints[i], 16, 100 * (i + 1));
                clickableNodes.add(node);
                this.getChildren().add(node);
                drawCircle(gc, xPoints[i], yPoints[i], 16);
                drawCircle(gc, xPoints[i], yPoints[i], 12);
            }

            // Intermediate path nodes between corners
            int interval1X = (xPoints[3] - xPoints[2]) / 5;
            int interval1Y = (yPoints[3] - yPoints[2]) / 5;
            int interval2X = (xPoints[2] - xPoints[1]) / 5;

            for (int i = 1; i < 5; i++) {
                int x1 = xPoints[0] + interval1X * i;
                int y1 = yPoints[0] - interval1Y * i;
                ClickableNodeFX node1 = new ClickableNodeFX(x1, y1, 9, 120 + i);
                clickableNodes.add(node1);
                this.getChildren().add(node1);
                drawCircle(gc, x1, y1, 9);

                int x2 = xPoints[1] + interval2X * i;
                int y2 = yPoints[1];
                ClickableNodeFX node2 = new ClickableNodeFX(x2, y2, 9, 230 + i);
                clickableNodes.add(node2);
                this.getChildren().add(node2);
                drawCircle(gc, x2, y2, 9);

                int x3 = xPoints[2] + interval1X * i;
                int y3 = yPoints[2] + interval1Y * i;
                ClickableNodeFX node3 = new ClickableNodeFX(x3, y3, 9, 340 + i);
                clickableNodes.add(node3);
                this.getChildren().add(node3);
                drawCircle(gc, x3, y3, 9);

                int x4 = xPoints[3] - interval1X * i;
                int y4 = yPoints[3] + interval1Y * i;
                ClickableNodeFX node4 = new ClickableNodeFX(x4, y4, 9, 450 + i);
                clickableNodes.add(node4);
                this.getChildren().add(node4);
                drawCircle(gc, x4, y4, 9);

                int x5 = xPoints[4] - interval2X * i;
                int y5 = yPoints[4];
                ClickableNodeFX node5 = new ClickableNodeFX(x5, y5, 9, 560 + i);
                clickableNodes.add(node5);
                this.getChildren().add(node5);
                drawCircle(gc, x5, y5, 9);

                int x6 = xPoints[5] - interval1X * i;
                int y6 = yPoints[5] - interval1Y * i;
                ClickableNodeFX node6 = new ClickableNodeFX(x6, y6, 9, 610 + i);
                clickableNodes.add(node6);
                this.getChildren().add(node6);
                drawCircle(gc, x6, y6, 9);
            }

            // Inner diagonals (toward center)
            int intervalC1X = (cx - xPoints[1]) / 3;
            int intervalC1Y = (cy - yPoints[1]) / 3;
            int intervalC2X = (cx - xPoints[0]) / 3;

            for (int i = 1; i < 3; i++) {
                int m1x = cx - intervalC2X * i;
                int m1y = cy;
                ClickableNodeFX node1 = new ClickableNodeFX(m1x, m1y, 9, 710 + i);
                clickableNodes.add(node1);
                this.getChildren().add(node1);
                drawCircle(gc, m1x, m1y, 9);

                int m2x = xPoints[1] + intervalC1X * i;
                int m2y = yPoints[1] + intervalC1Y * i;
                ClickableNodeFX node2 = new ClickableNodeFX(m2x, m2y, 9, 270 + i);
                clickableNodes.add(node2);
                this.getChildren().add(node2);
                drawCircle(gc, m2x, m2y, 9);

                int m3x = xPoints[2] - intervalC1X * i;
                int m3y = yPoints[2] + intervalC1Y * i;
                ClickableNodeFX node3 = new ClickableNodeFX(m3x, m3y, 9, 370 + i);
                clickableNodes.add(node3);
                this.getChildren().add(node3);
                drawCircle(gc, m3x, m3y, 9);

                int m4x = xPoints[3] - intervalC2X * i;
                int m4y = yPoints[3];
                ClickableNodeFX node4 = new ClickableNodeFX(m4x, m4y, 9, 470 + i);
                clickableNodes.add(node4);
                this.getChildren().add(node4);
                drawCircle(gc, m4x, m4y, 9);

                int m5x = xPoints[4] - intervalC1X * i;
                int m5y = yPoints[4] - intervalC1Y * i;
                ClickableNodeFX node5 = new ClickableNodeFX(m5x, m5y, 9, 570 + i);
                clickableNodes.add(node5);
                this.getChildren().add(node5);
                drawCircle(gc, m5x, m5y, 9);

                int m6x = cx - intervalC1X * i;
                int m6y = cy + intervalC1Y * i;
                ClickableNodeFX node6 = new ClickableNodeFX(m6x, m6y, 9, 760 + i);
                clickableNodes.add(node6);
                this.getChildren().add(node6);
                drawCircle(gc, m6x, m6y, 9);
            }

            // Center
            ClickableNodeFX node = new ClickableNodeFX(cx, cy, 16, 700);
            clickableNodes.add(node);
            this.getChildren().add(node);
            drawCircle(gc, cx, cy, 16);
            drawCircle(gc, cx, cy, 12);
            drawText(gc, "출발", xPoints[0], yPoints[0]);
        }

    }

    private void drawCircle(GraphicsContext gc, int x, int y, int r) {
        gc.setFill(Color.WHITE);
        gc.fillOval(x - r, y - r, 2 * r, 2 * r);
        gc.setStroke(Color.BLACK);
        gc.strokeOval(x - r, y - r, 2 * r, 2 * r);
    }

    private void drawText(GraphicsContext gc, String text, int x, int y) {
        Text tempText = new Text(text);
        tempText.setFont(Font.getDefault());
        double textWidth = tempText.getLayoutBounds().getWidth();
        double textHeight = tempText.getLayoutBounds().getHeight();
        gc.setFill(Color.BLACK);
        gc.fillText(text, x - textWidth / 2, y + textHeight / 4);
    }
}