package com.yut.ui.javaFX;

import com.yut.model.Board;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.animation.*;
import javafx.util.Duration;
import javafx.scene.Node;

import javafx.scene.text.Font;

public class StartScreenFX extends StackPane {

    private int boardType = 4;

    // Track the current image index
    Integer currentPlayerIndex = 0;
    // Track the current image index
    Integer currentPieceIndex = 0;

    private Runnable startButtonAction;

    public StartScreenFX() {
        VBox layout = new VBox();

        Font customFont20 = Font.loadFont(
                getClass().getResource("/assets/fonts/SF_HailSnow.ttf").toExternalForm(),
                20);

        Font customFont50 = Font.loadFont(
                getClass().getResource("/assets/fonts/SF_HailSnow.ttf").toExternalForm(),
                40);

        layout.setSpacing(30);
        layout.setPadding(new Insets(30));
        layout.setAlignment(Pos.TOP_CENTER);

        Image image = new Image(getClass().getResource("/assets/img/yut!_logo.png").toExternalForm());
        ImageView titleImage = new ImageView(image);
        titleImage.setFitWidth(500);
        titleImage.setPreserveRatio(true);

        ScaleTransition scale = new ScaleTransition(Duration.seconds(1.2), titleImage);
        scale.setFromX(1.0);
        scale.setFromY(1.0);
        scale.setToX(1.2);
        scale.setToY(1.2);
        scale.setCycleCount(ScaleTransition.INDEFINITE);
        scale.setAutoReverse(true);
        scale.play();

        // titleBox
        HBox titleBox = new HBox();
        titleBox.setAlignment(Pos.TOP_CENTER); // center the label horizontally
        titleBox.setPadding(new Insets(60, 0, 20, 0)); // optional padding
        titleBox.getChildren().add(titleImage); // add the Label into the box
        layout.getChildren().add(titleBox);

        // middleBox
        HBox middleBox = new HBox(10);
        middleBox.setAlignment(Pos.TOP_LEFT);
        VBox.setMargin(middleBox, new Insets(50, 0, 0, 0));
        layout.getChildren().add(middleBox);

        // optionBox
        VBox optionBox = new VBox(30);
        optionBox.setAlignment(Pos.CENTER_LEFT);
        HBox.setMargin(optionBox, new Insets(0, 0, 0, 100));
        middleBox.getChildren().add(optionBox);

        Label playerLabel = new Label("인원 수:");
        playerLabel.setFont(customFont50);

        Label pieceLabel = new Label("말 수:");
        pieceLabel.setFont(customFont50);

        optionBox.getChildren().addAll(playerLabel, pieceLabel);

        // choiceBox

        VBox choiceBox = new VBox(0);
        choiceBox.setAlignment(Pos.CENTER);
        HBox.setMargin(choiceBox, new Insets(0, 0, 0, 30));
        middleBox.getChildren().add(choiceBox);

        // Preload images into an array
        Image[] playerImages = new Image[5];
        for (int i = 1; i <= 4; i++) {
            playerImages[i] = new Image(
                    getClass().getResource("/assets/img/retro_num_button_" + (i) + ".png").toExternalForm());
        }

        ImageView playerImageView = new ImageView(playerImages[2]);
        playerImageView.setFitWidth(60);
        playerImageView.setPreserveRatio(true);
        playerImageView.setPickOnBounds(true);

        // Change image on click
        playerImageView.setOnMouseClicked(event -> {
            currentPlayerIndex = (currentPlayerIndex + 1) % (playerImages.length - 2);
            playerImageView.setImage(playerImages[currentPlayerIndex + 2]);
        });

        choiceBox.getChildren().addAll(playerImageView);

        Image[] pieceImages = new Image[6];
        for (int i = 1; i <= 5; i++) {
            pieceImages[i] = new Image(
                    getClass().getResource("/assets/img/retro_num_button_" + (i) + ".png").toExternalForm());
        }

        ImageView pieceImageView = new ImageView(playerImages[2]);
        pieceImageView.setFitWidth(60);
        pieceImageView.setPreserveRatio(true);
        pieceImageView.setPickOnBounds(true);

        // Change image on click
        pieceImageView.setOnMouseClicked(event -> {
            currentPieceIndex = (currentPieceIndex + 1) % (pieceImages.length - 2);
            pieceImageView.setImage(pieceImages[currentPieceIndex + 2]);
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
        VBox.setMargin(gameStart, new Insets(30, 0, 20, 0));

        Button gameStartBtn = new Button("게임 시작!");

        gameStartBtn.setPrefWidth(160); // width in pixels
        gameStartBtn.setPrefHeight(60); // height in pixels
        gameStartBtn.setPadding(new Insets(0, 20, 0, 20));
        gameStartBtn.setFont(customFont20);
        gameStartBtn.setStyle("-fx-font-size: 20px;");

        ScaleTransition pressAnimation = new ScaleTransition(Duration.millis(100), gameStartBtn);
        pressAnimation.setToX(1.1);
        pressAnimation.setToY(1.1);

        ScaleTransition releaseAnimation = new ScaleTransition(Duration.millis(100), gameStartBtn);
        releaseAnimation.setToX(1.0);
        releaseAnimation.setToY(1.0);
        gameStartBtn.setOnMousePressed(e -> pressAnimation.playFromStart());
        gameStartBtn.setOnMouseReleased(e -> releaseAnimation.playFromStart());
        gameStartBtn.setOnAction(e -> {
            if (startButtonAction != null) {
                startButtonAction.run();
            }
        });

        gameStart.getChildren().add(gameStartBtn); // ✔ Add button to HBox
        layout.getChildren().add(gameStart); // ✔ Add HBox to parent layout (e.g., VBox)

        BackgroundImage backgroundImage = new BackgroundImage(
                new javafx.scene.image.Image(getClass().getResource("/assets/img/background_empty.png").toExternalForm()),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, false, true));
        setBackground(new Background(backgroundImage));

        Image yut1 = new Image(getClass().getResource("/assets/img/yut_large_upward.png").toExternalForm());
        Image yut2 = new Image(getClass().getResource("/assets/img/yut_large_upward.png").toExternalForm());
        Image yut3 = new Image(getClass().getResource("/assets/img/yut_large_backdo.png").toExternalForm());
        Image yut4 = new Image(getClass().getResource("/assets/img/yut_large_backward.png").toExternalForm());

        ImageView yut1ImageView = new ImageView(yut1);
        ImageView yut2ImageView = new ImageView(yut2);
        ImageView yut3ImageView = new ImageView(yut3);
        ImageView yut4ImageView = new ImageView(yut4);

        yut1ImageView.setFitHeight(500);
        yut1ImageView.setPreserveRatio(true);
        yut2ImageView.setFitHeight(459);
        yut2ImageView.setPreserveRatio(true);
        yut3ImageView.setFitHeight(412);
        yut3ImageView.setPreserveRatio(true);
        yut4ImageView.setFitHeight(522);
        yut4ImageView.setPreserveRatio(true);


        FallingYutFX fallingYut1 = new FallingYutFX(yut1ImageView, -300, -350 , -200, 600,
                30, 360, 23);
        FallingYutFX fallingYut2 = new FallingYutFX(yut2ImageView, 75, 90 , -25, 600,
                60, -360, 19);
        FallingYutFX fallingYut3 = new FallingYutFX(yut3ImageView, -300, 50 , -200, 600,
                90, 360, 24);
        FallingYutFX fallingYut4 = new FallingYutFX(yut4ImageView, 350, -230 , 250, 600,
                120, -360, 21);

        //fallingYut1.setMouseTransparent(true);
        getChildren().addAll(fallingYut1.getNode(), fallingYut2.getNode(), fallingYut3.getNode(), fallingYut4.getNode(),
                layout);
        //getChildren().add(0, fallingYut1.getNode());


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
        return currentPlayerIndex + 2;
    }

    public int getPieceCount() {
        return currentPieceIndex + 2;
    }

    public void setStartButtonListener(Runnable action) {
        this.startButtonAction = action;
    }

}