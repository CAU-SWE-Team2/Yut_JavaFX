package com.yut.ui.javaFX;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class ControlPanelFX extends VBox {
    private final Button[] yutButtons = new Button[6];
    private final Button randomButton;
    private final Button selectButton;
    private final Button moveNewPieceButton;
    private final Button goalButton;

    public Button[] getYutButtons() {
        return yutButtons;
    }

    public Button getRandomButton() {
        return randomButton;
    }

    public Button getSelectButton() {
        return selectButton;
    }

    public Button getMoveNewPieceButton() {
        return moveNewPieceButton;
    }

    public Button getGoalButton() {
        return goalButton;
    }

    public void highlightYutButton(int yut) {
        if (yut < 0 || yut >= yutButtons.length)
            throw new IllegalArgumentException("yut index out of range: " + yut);

        for (Button b : yutButtons) {
            b.setStyle(""); // reset style
            b.setDisable(true); // disable all
        }

        Button highlight = yutButtons[yut];
        highlight.setDisable(false);
        highlight.setStyle("-fx-background-color: yellow;");
    }

    public ControlPanelFX() {
        setSpacing(20);
        setPadding(new Insets(20));

        // 1. 랜덤 & 지정 버튼 (HBox)
        HBox throwButtonsPanel = new HBox(10);
        throwButtonsPanel.setPadding(new Insets(10, 0, 0, 0));
        randomButton = new Button("랜덤 던지기");
        randomButton.setPrefSize(150, 40);
        selectButton = new Button("지정 던지기");
        selectButton.setPrefSize(150, 40);
        throwButtonsPanel.getChildren().addAll(randomButton, selectButton);

        // 2. 윷 결과 버튼들 (빽도 ~ 모)
        HBox choosePanel = new HBox(5);
        String[] yutResults = { "빽도", "도", "개", "걸", "윷", "모" };
        for (int i = 0; i < yutResults.length; i++) {
            Button b = new Button(yutResults[i]);
            b.setDisable(true);
            b.setPrefSize(60, 30);
            yutButtons[i] = b;
            choosePanel.getChildren().add(b);
        }

        // 3. 골 버튼, 새 말 움직이기 버튼
        HBox bottomButtonPanel = new HBox(10);
        bottomButtonPanel.setPadding(new Insets(10, 0, 0, 0));
        goalButton = new Button("골인!");
        goalButton.setPrefSize(150, 40);
        goalButton.setVisible(false);

        moveNewPieceButton = new Button("새로운 말 움직이기");
        moveNewPieceButton.setPrefSize(150, 40);
        bottomButtonPanel.getChildren().addAll(goalButton, moveNewPieceButton);

        // 지정 버튼 클릭 시 윷 버튼 활성화
        selectButton.setOnAction(e -> {
            for (Button b : yutButtons) {
                b.setDisable(false);
            }
        });

        getChildren().addAll(throwButtonsPanel, choosePanel, bottomButtonPanel);
    }
}
