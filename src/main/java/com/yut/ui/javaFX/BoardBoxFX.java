package com.yut.ui.javaFX;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;

public class BoardBoxFX extends VBox {
    private static BoardBoxFX currentlySelected = null;
    static int selectedBoardType = 4;
    private int width = 80;
    private int height = 200;
    private int boardType;

    private Label label;
    private Line underline;
    private Line topline;

    Font customFont = Font.loadFont(
            getClass().getResource("/assets/fonts/SF_HailSnow.ttf").toExternalForm(),
            30);

    BoardBoxFX(String label, int boardType) {
        this.boardType = boardType;
        setAlignment(Pos.CENTER);

        this.topline = new Line();
        this.topline.setStartX(20);
        this.topline.setEndX(width - 20);
        this.topline.setStrokeWidth(1);
        this.topline.setStroke(Color.web("#C16D00"));
        this.topline.setStrokeWidth(3);
        this.topline.setVisible(false); // hidden by default

        this.label = new Label(label);
        this.label.setFont(customFont);
        this.label.setPrefWidth(width);
        this.label.setPrefHeight(height);

        this.label.setFont(customFont);

        this.label.setAlignment(Pos.CENTER);

        this.underline = new Line();
        this.underline.setStartX(20);
        this.underline.setEndX(width - 20);
        this.underline.setStrokeWidth(1);
        this.underline.setStroke(Color.web("#C16D00"));
        this.underline.setStrokeWidth(3);
        this.underline.setVisible(false); // hidden by default

        if (selectedBoardType == this.boardType) {
            currentlySelected = this;
            currentlySelected.underline.setVisible(true);
            currentlySelected.topline.setVisible(true);
        }

        this.label.setOnMouseClicked(event -> {
            selectedBoardType = this.boardType;
            if (currentlySelected != null) {
                currentlySelected.underline.setVisible(false);
                currentlySelected.topline.setVisible(false);
            }
            underline.setVisible(true);
            topline.setVisible(true);
            currentlySelected = this;
        });

        getChildren().addAll(this.topline, this.label, this.underline);
    }
}
