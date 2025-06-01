package com.yut;

import com.yut.controller.TitleController;
import com.yut.ui.javaFX.GameScreenFX;
import com.yut.ui.javaFX.StartScreenFX;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {

        StartScreenFX startScreen = new StartScreenFX();
        Scene scene = new Scene(startScreen, 950, 760);
        scene.getStylesheets().add(
                getClass().getResource("/com/yut/ui/CSS/StartScreenFX.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.setTitle("Yut Game - Game Screen");
        primaryStage.show();

        TitleController titleController = new TitleController(startScreen);
        titleController.start();
        
    }

    public static void main(String[] args) {
        launch(args);
    }
}
