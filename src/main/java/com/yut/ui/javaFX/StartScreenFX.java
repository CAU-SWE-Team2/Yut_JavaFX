package com.yut.ui.javaFX;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

public class StartScreenFX extends VBox {

    private int boardType = 4;
    private int playerCount = 2;
    private int pieceCount = 4;

    private Runnable startButtonAction;

    public StartScreenFX() {
        setSpacing(30);
        setPadding(new Insets(30));
        setAlignment(Pos.TOP_CENTER);

        Label title = new Label("Start Screen");
        title.setFont(new Font(24));
        getChildren().add(title);

        HBox boardButtonBox = new HBox(30);
        boardButtonBox.setAlignment(Pos.CENTER);

        ToggleGroup boardGroup = new ToggleGroup();
        RadioButton board4 = new RadioButton("4각형");
        RadioButton board5 = new RadioButton("5각형");
        RadioButton board6 = new RadioButton("6각형");

        board4.setToggleGroup(boardGroup);
        board5.setToggleGroup(boardGroup);
        board6.setToggleGroup(boardGroup);
        board4.setSelected(true);

        boardButtonBox.getChildren().addAll(board4, board5, board6);
        getChildren().add(boardButtonBox);

        boardGroup.selectedToggleProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal == board4)
                boardType = 4;
            else if (newVal == board5)
                boardType = 5;
            else if (newVal == board6)
                boardType = 6;
        });

        VBox configBox = new VBox(20);
        configBox.setAlignment(Pos.CENTER);

        Label playerLabel = new Label("플레이어 수:");
        Spinner<Integer> playerSpinner = new Spinner<>(2, 4, 2);
        playerSpinner.valueProperty().addListener((obs, oldVal, newVal) -> playerCount = newVal);

        Label pieceLabel = new Label("말 개수:");
        Spinner<Integer> pieceSpinner = new Spinner<>(2, 5, 4);
        pieceSpinner.valueProperty().addListener((obs, oldVal, newVal) -> pieceCount = newVal);

        configBox.getChildren().addAll(playerLabel, playerSpinner, pieceLabel, pieceSpinner);
        getChildren().add(configBox);

        Button startButton = new Button("게임 시작");
        startButton.setPrefSize(150, 40);
        startButton.setOnAction(e -> {
            if (startButtonAction != null) {
                startButtonAction.run();
            }
        });

        getChildren().add(startButton);
    }

    public int getBoardType() {
        return boardType;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public int getPieceCount() {
        return pieceCount;
    }

    public void setStartButtonAction(Runnable action) {
        this.startButtonAction = action;
    }
}
