package com.yut;

import com.yut.ui.javaFX.GameScreenFX;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        GameScreenFX gameScreen = new GameScreenFX(4, 2, () -> {
        });

        Scene scene = new Scene(gameScreen, 1100, 760);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Yut Game - Game Screen");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
