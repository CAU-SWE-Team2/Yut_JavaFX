package com.yut.ui.javaFX;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
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

        this.setPrefSize(180, 80);
        this.canvas = new Canvas(180, 80);
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

        // 배경 사각형
        gc.setFill(Color.rgb(230, 230, 230));
        gc.fillRoundRect(10, 10, canvas.getWidth() - 20, canvas.getHeight() - 20, 20, 15);

        // 테두리
        gc.setStroke(highlighted ? Color.YELLOW : Color.GRAY);
        gc.setLineWidth(highlighted ? 3 : 1);
        gc.strokeRoundRect(10, 10, canvas.getWidth() - 20, canvas.getHeight() - 20, 20, 15);

        // 플레이어 ID
        gc.setFill(Color.BLACK);
        gc.setFont(Font.font(14));
        gc.fillText("P" + playerID, 20, 25);

        // 말 개수 점 표시
        int radius = 10;
        int spacing = 5;
        int totalWidth = pieceCount * (2 * radius) + (pieceCount - 1) * spacing;
        int startX = (int) ((canvas.getWidth() - totalWidth) / 2);
        int y = 45;

        for (int i = 0; i < pieceCount; i++) {
            int x = startX + i * (2 * radius + spacing);
            gc.setFill(color);
            gc.fillOval(x, y, 2 * radius, 2 * radius);
            gc.setStroke(Color.DARKGRAY);
            gc.strokeOval(x, y, 2 * radius, 2 * radius);
        }
    }
}
