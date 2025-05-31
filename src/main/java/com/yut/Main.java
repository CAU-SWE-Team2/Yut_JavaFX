package com.yut;

import com.yut.ui.javaFX.GameScreenFX;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        GameScreenFX gameScreen = new GameScreenFX(4, 3, () -> {
            // 시작 화면으로 돌아가는 기능 구현 시 작성
        });

        Scene scene = new Scene(gameScreen, 950, 760);
        scene.getStylesheets().add(
                getClass().getResource("/com/yut/ui/CSS/StartScreenFX.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.setTitle("Yut Game - Game Screen");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
