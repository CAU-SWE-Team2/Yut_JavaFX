package com.yut.ui.javaFX;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EndingStageFX extends Stage {

    private final int winner;
    private final Button restartButton = new Button("Restart");
    private final Button exitButton = new Button("Exit");

    public EndingStageFX(int winner) {
        this.winner = winner;

        Label winnerLabel = new Label("Player " + winner + " wins!");
        winnerLabel.setStyle("-fx-font-size: 20px; -fx-padding: 20px;");

        HBox buttonBox = new HBox(10, restartButton, exitButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setStyle("-fx-padding: 20px;");

        VBox root = new VBox(20, winnerLabel, buttonBox);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 400, 200);
        setTitle("Game Over");
        setScene(scene);
        centerOnScreen();

        exitButton.setOnAction(e -> System.exit(0));
        restartButton.setOnAction(e -> this.close());
    }

    public void addRestartButtonListener(Runnable action) {
        restartButton.setOnAction(e -> {
            action.run();
            this.close();
        });
    }
}
