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

        primaryStage.setTitle("Yut Game - Game Screen");
        primaryStage.setResizable(false);
        primaryStage.show();

        TitleController titleController = new TitleController(primaryStage);
        titleController.start();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
