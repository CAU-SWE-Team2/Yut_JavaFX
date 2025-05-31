package com.yut.ui.javaFX;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import com.yut.ui.javaFX.ClickableNodeFX;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class BoardCanvasFX extends Region {
    private int boardType;
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

    public BoardCanvasFX(int boardType) {
        this.boardType = boardType;
        this.setPrefSize(400, 400);
        canvas = new Canvas(400, 400);
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
            int cStep = boardSize / 5;
            int dStep = boardSize / 6;
            startX = padding;
            startY = padding;

            gc.setStroke(Color.BLACK);
            gc.strokeRect(startX, startY, boardSize, boardSize);
            gc.strokeLine(startX, startY, startX + boardSize, startY + boardSize);
            gc.strokeLine(startX, startY + boardSize, startX + boardSize, startY);

            // Draw outer circles
            for (int i = 0; i < 5; i++) {
                drawCircle(gc, startX + 5 * cStep, startY + (5 - i) * cStep, i == 0 ? 16 : 9);
                drawCircle(gc, startX + (5 - i) * cStep, startY, i == 0 ? 16 : 9);
                drawCircle(gc, startX, startY + i * cStep, i == 0 ? 16 : 9);
                drawCircle(gc, startX + i * cStep, startY + 5 * cStep, i == 0 ? 16 : 9);
            }

            for (int row = 1; row <= 5; row++) {
                for (int col = 1; col <= 5; col++) {
                    boolean diagTLtoC = (row == col) && (row < 3);
                    boolean diagTRtoC = (row + col == 6) && (row < 3);
                    boolean diagCtoBR = (row == col) && (row > 3);
                    boolean diagCtoBL = (row + col == 6) && (row > 3);

                    if (diagTLtoC || diagTRtoC || diagCtoBR || diagCtoBL) {
                        drawCircle(gc, startX + col * dStep, startY + row * dStep, 9);
                    }
                }
            }

            drawCircle(gc, startX + 3 * dStep, startY + 3 * dStep, 16);
            drawText(gc, "출발", startX + 5 * cStep, startY + 5 * cStep);
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
