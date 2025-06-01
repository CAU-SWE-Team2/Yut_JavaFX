package com.yut.ui.javaFX;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class PieceFX extends Pane {
    private int playerID;
    private Color color;
    private int x;
    private int y;
    public static final int radius = 10;
    private int cnt = 1;

    private final Canvas canvas;

    public PieceFX(int x, int y, Color color, int cnt) {
        this.color = color;
        this.x = x;
        this.y = y;
        this.cnt = cnt;

        setPrefSize(radius * 2, radius * 2);
        this.canvas = new Canvas(radius * 2, radius * 2);
        this.getChildren().add(canvas);
        setLayoutX(x);
        setLayoutY(y);
        this.setMouseTransparent(true);
        draw();
    }

    public void setCoords(int x, int y) {
        this.x = x;
        this.y = y;
        setLayoutX(x);
        setLayoutY(y);
    }

    private void draw() {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // 말 원 그리기
        double centerX = radius;
        double centerY = radius;
        double[] xPoints = new double[8];
        double[] yPoints = new double[8];

        for (int i = 0; i < 8; i++) {
            double angle = Math.toRadians(45 * i); // 360도 / 8 = 45도 간격
            xPoints[i] = centerX + radius * Math.cos(angle);
            yPoints[i] = centerY + radius * Math.sin(angle);
        }

        // 색 채우기
        gc.setFill(color);
        gc.fillPolygon(xPoints, yPoints, 8);

        // 테두리 그리기
        gc.setStroke(Color.DARKGRAY);
        gc.setLineWidth(1.5);
        gc.strokePolygon(xPoints, yPoints, 8);

        // 말 개수 텍스트
        gc.setFill(Color.BLACK);
        Font font = Font.font("SansSerif", FontWeight.BOLD, 14);
        gc.setFont(font);
        String text = String.valueOf(cnt);

        // 문자열 크기 계산
        Text helperText = new Text(text);
        helperText.setFont(font);
        double textWidth = helperText.getLayoutBounds().getWidth();
        double textHeight = helperText.getLayoutBounds().getHeight();

        double textX = (canvas.getWidth() - textWidth) / 2;
        double textY = (canvas.getHeight() + textHeight) / 2 - 2;

        gc.fillText(text, textX, textY);
    }
}
