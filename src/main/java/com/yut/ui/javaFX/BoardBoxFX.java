package com.yut.ui.javaFX;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;

public class BoardBoxFX extends VBox {
    private static BoardBoxFX currentlySelected = null;
    private int width = 80;
    private int height = 200;
    private int boardType;

    private Label label;
    private Line underline;

    Font customFont = Font.loadFont(
            getClass().getResource("/fonts/SF_HailSnow.ttf").toExternalForm(),
            30);

    BoardBoxFX(String label) {
        setAlignment(Pos.CENTER);
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
        this.underline.setVisible(false); // hidden by default

        this.label.setOnMouseClicked(event -> {
            this.boardType = 4;
            if (currentlySelected != null) {
                currentlySelected.underline.setVisible(false);
            }
            underline.setVisible(true);
            currentlySelected = this;
        });

        getChildren().addAll(this.label, this.underline);
    }
}
