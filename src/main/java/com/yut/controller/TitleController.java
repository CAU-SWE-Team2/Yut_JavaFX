package com.yut.controller;

import javax.swing.SwingUtilities;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.yut.ui.javaFX.GameScreenFX;
import com.yut.ui.javaFX.StartScreenFX;
import javafx.stage.Stage;

import javafx.scene.Scene;

import com.yut.controller.model_interfaces.GameModelInterface;
import com.yut.model.Game;

public class TitleController{
    
    StartScreenFX startScreen;

    GameModelInterface gameModel;
    GameControllerFX gameController;
    Stage primaryStage;

    public TitleController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void start(){
        StartScreenFX startScreen = new StartScreenFX();
        Scene startScene = new Scene(startScreen, 950, 760);
        startScene.getStylesheets().add(
                getClass().getResource("/com/yut/ui/CSS/StartScreenFX.css").toExternalForm());
        primaryStage.setScene(startScene);



        startScreen.setStartButtonListener(() -> {
            int boardType = startScreen.getBoardType();
            int playerCount = startScreen.getPlayerCount();
            int pieceCount = startScreen.getPieceCount();

            // System.out.println("Board Type: " + boardType);
            // System.out.println("Player Count: " + playerCount);
            // System.out.println("Piece Count: " + pieceCount);

            Game gameModel = new Game(boardType, playerCount, pieceCount);

            GameScreenFX gameScreen = new GameScreenFX(startScene, boardType, playerCount, pieceCount);
            Scene gameScene = new Scene(gameScreen, 950, 760);


            gameScene.getStylesheets().add(
                    getClass().getResource("/com/yut/ui/CSS/StartScreenFX.css").toExternalForm());


            primaryStage.setScene(gameScene);
            primaryStage.show();

            gameController = new GameControllerFX(gameModel, gameScreen, primaryStage, startScene);
        });

    }

}