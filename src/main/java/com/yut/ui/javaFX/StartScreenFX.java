package com.yut.ui.javaFX;

import com.yut.model.Board;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

public class StartScreenFX extends VBox {

    private int boardType = 4;

    // Track the current image index
    final int[] currentPlayerIndex = { 0 };
    // Track the current image index
    final int[] currentPieceIndex = { 0 };

    private Runnable startButtonAction;

    public StartScreenFX() {
        Font customFont20 = Font.loadFont(
                getClass().getResource("/assets/fonts/SF_HailSnow.ttf").toExternalForm(),
                20);

        Font customFont50 = Font.loadFont(
                getClass().getResource("/assets/fonts/SF_HailSnow.ttf").toExternalForm(),
                50);

        setSpacing(30);
        setPadding(new Insets(30));
        setAlignment(Pos.TOP_CENTER);

        Image image = new Image(getClass().getResource("/assets/img/yut!_logo.png").toExternalForm());
        ImageView titleImage = new ImageView(image);
        titleImage.setFitWidth(500);
        titleImage.setPreserveRatio(true);

        // titleBox
        HBox titleBox = new HBox();
        titleBox.setAlignment(Pos.TOP_CENTER); // center the label horizontally
        titleBox.setPadding(new Insets(60, 0, 20, 0)); // optional padding
        titleBox.getChildren().add(titleImage); // add the Label into the box
        getChildren().add(titleBox);

        /*
         * titleBox.setStyle(
         * "-fx-border-color: blue;" +
         * "-fx-border-width: 3px;" +
         * "-fx-border-radius: 5px;" + // rounded corners
         * "-fx-background-color: lightgray;" // optional background to see it better
         * );
         * 
         */

        // middleBox
        HBox middleBox = new HBox(10);
        // middleBox.setAlignment(Pos.CENTER);
        VBox.setMargin(middleBox, new Insets(100, 0, 0, 0));
        getChildren().add(middleBox);

        // optionBox
        VBox optionBox = new VBox(30);
        optionBox.setAlignment(Pos.CENTER_LEFT);
        HBox.setMargin(optionBox, new Insets(0, 0, 0, 200));
        middleBox.getChildren().add(optionBox);

        Label playerLabel = new Label("인원 수:");
        playerLabel.setFont(customFont50);

        Label pieceLabel = new Label("말 수:");
        pieceLabel.setFont(customFont50);

        optionBox.getChildren().addAll(playerLabel, pieceLabel);

        // choiceBox

        VBox choiceBox = new VBox(0);
        HBox.setMargin(choiceBox, new Insets(0, 0, 0, 100));
        middleBox.getChildren().add(choiceBox);

        // Preload images into an array
        Image[] playerImages = new Image[4];
        for (int i = 0; i < 4; i++) {
            playerImages[i] = new Image(
                    getClass().getResource("/assets/img/retro_num_button_" + (i + 1) + ".png").toExternalForm());
        }

        ImageView playerImageView = new ImageView(playerImages[0]);
        playerImageView.setFitWidth(100);
        playerImageView.setPreserveRatio(true);
        playerImageView.setPickOnBounds(true);

        // Change image on click
        playerImageView.setOnMouseClicked(event -> {
            currentPlayerIndex[0] = (currentPlayerIndex[0] + 1) % playerImages.length;
            playerImageView.setImage(playerImages[currentPlayerIndex[0]]);
        });

        choiceBox.getChildren().addAll(playerImageView);

        Image[] pieceImages = new Image[5];
        for (int i = 0; i < 5; i++) {
            pieceImages[i] = new Image(
                    getClass().getResource("/assets/img/retro_num_button_" + (i + 1) + ".png").toExternalForm());
        }

        ImageView pieceImageView = new ImageView(playerImages[0]);
        pieceImageView.setFitWidth(100);
        pieceImageView.setPreserveRatio(true);
        pieceImageView.setPickOnBounds(true);

        // Change image on click
        pieceImageView.setOnMouseClicked(event -> {
            currentPieceIndex[0] = (currentPieceIndex[0] + 1) % pieceImages.length;
            pieceImageView.setImage(pieceImages[currentPieceIndex[0]]);
        });

        choiceBox.getChildren().addAll(pieceImageView);

        // boardBox
        HBox boardBox = new HBox(10);
        // boardBox.setAlignment(Pos.CENTER_LEFT);
        HBox.setMargin(boardBox, new Insets(0, 0, 0, 220)); // Top = 100px

        BoardBoxFX board4Box = new BoardBoxFX("사\n각\n형\n판", 4);
        BoardBoxFX board5Box = new BoardBoxFX("오\n각\n형\n판", 5);
        BoardBoxFX board6Box = new BoardBoxFX("육\n각\n형\n판", 6);

        boardBox.getChildren().addAll(board4Box, board5Box, board6Box);
        middleBox.getChildren().add(boardBox);

        // gameStart
        HBox gameStart = new HBox(20);
        gameStart.setAlignment(Pos.BOTTOM_CENTER);
        // gameStart.setPadding(new Insets(0, 0, 0, 0));
        VBox.setMargin(gameStart, new Insets(100, 0, 0, 0));

        Button gameStartBtn = new Button("게임 시작!");

        gameStartBtn.setPrefWidth(120); // width in pixels
        gameStartBtn.setPrefHeight(60); // height in pixels
        gameStartBtn.setFont(customFont20);
        gameStartBtn.setOnAction(e -> {
            if (startButtonAction != null) {
                startButtonAction.run();
            }
        });

        gameStart.getChildren().add(gameStartBtn); // ✔ Add button to HBox
        getChildren().add(gameStart); // ✔ Add HBox to parent layout (e.g., VBox)

        BackgroundImage backgroundImage = new BackgroundImage(
            new javafx.scene.image.Image(getClass().getResource("/assets/img/background.png").toExternalForm()),
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            new BackgroundSize(100, 100, true, true, false, true)
        );
        setBackground(new Background(backgroundImage));

    }

    private void highlight(Button selected, Button... others) {
        selected.setStyle("-fx-border-color: blue; -fx-border-width: 3;");
        for (Button btn : others) {
            btn.setStyle(""); // Clear highlight
        }
    }

    public int getBoardType() {
        return BoardBoxFX.selectedBoardType;
    }

    // index starts from 0 (index 0 is retro_num_button_2.png)
    public int getPlayerCount() {
        return currentPieceIndex[0] + 1;
    }

    public int getPieceCount() {
        return currentPieceIndex[0] + 1;
    }

    public void setStartButtonListener(Runnable action) {
        this.startButtonAction = action;
    }

}