package com.yut.ui.javaFX;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;

public class PlayerCanvasFX extends Pane {
    private int playerID;
    private int pieceCount;
    private Color color;
    private boolean highlighted = false;

    private final Canvas canvas;

    public PlayerCanvasFX(int playerID, int pieceCount, Color color) {
        this.playerID = playerID;
        this.pieceCount = pieceCount;
        this.color = color;

        this.setPrefSize(150, 180);
        this.canvas = new Canvas(150, 180);
        this.getChildren().add(canvas);

        draw();
    }

    public void setHighlighted(boolean highlighted) {
        this.highlighted = highlighted;
        draw();
    }

    public void setPieceCount(int count) {
        this.pieceCount = count;
        draw();
    }

    public Color getColor() {
        return color;
    }

    private void draw() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        if (highlighted) {
            gc.setFill(Color.rgb(81, 58, 29));
            gc.fillRoundRect(96, 10, canvas.getWidth() - 110, canvas.getHeight() - 20, 20, 15);
        } else {
            gc.setFill(Color.TRANSPARENT);
            gc.setStroke(Color.TRANSPARENT);
        }

        // 플레이어
        if (highlighted) {
            gc.setFill(Color.WHITE);
        } else {
            gc.setFill(Color.BLACK);

        }

        Font customFont16 = Font.loadFont(
                getClass().getResource("/assets/fonts/SF_HailSnow.ttf").toExternalForm(),
                16);
        gc.setFont(customFont16);

        String label = switch (playerID) {
            case 1 -> " 첫번째 놀이꾼";
            case 2 -> " 두번째 놀이꾼";
            case 3 -> " 세번째 놀이꾼";
            default -> " 네번째 놀이꾼";
        };

        double startX = 110; // 오른쪽 위치
        double startY = 30;
        double lineSpacing = 16;

        for (int i = 0; i < label.length(); i++) {
            char ch = label.charAt(i);
            if (ch == '\n') {
                startX += 16; // 다음 열로
                startY = 30;
            } else {
                gc.fillText(String.valueOf(ch), startX, startY);
                startY += lineSpacing;
            }
        }

        // 말 개수 점 표시
        int radius = 10;
        int spacing = 5;
        int totalHeight = pieceCount * (2 * radius) + (pieceCount - 1) * spacing;
        int startY2 = (int) ((canvas.getHeight() - totalHeight) / 2);
        int x = 52;

        for (int i = 0; i < pieceCount; i++) {
            int y = startY2 + i * (2 * radius + spacing);

            double centerX = x + radius;
            double centerY = y + radius;

            double[] xPoints = new double[8];
            double[] yPoints = new double[8];

            for (int j = 0; j < 8; j++) {
                double angle = Math.toRadians(45 * j);
                xPoints[j] = centerX + radius * Math.cos(angle);
                yPoints[j] = centerY + radius * Math.sin(angle);
            }

            gc.setFill(color);
            gc.fillPolygon(xPoints, yPoints, 8);
            gc.strokePolygon(xPoints, yPoints, 8);
        }

    }
}
