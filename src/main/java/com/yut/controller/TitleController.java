package com.yut.controller;

import javax.swing.SwingUtilities;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.yut.ui.javaFX.StartScreenFX;

import javafx.scene.Scene;

import com.yut.controller.model_interfaces.GameModelInterface;
import com.yut.model.Game;

public class TitleController{
    
    StartScreenFX startScreen;

    GameModelInterface gameModel;
    GameControllerFX gameController;

    public TitleController(StartScreenFX startScreen) {
        this.startScreen = startScreen;
    }

    public void start(){


        startScreen.setStartButtonListener(() -> {
        int boardType = startScreen.getBoardType();
        int playerCount = startScreen.getPlayerCount();
        int pieceCount = startScreen.getPieceCount();

        gameModel = new Game(boardType, playerCount, pieceCount);


        System.out.println("Board Type: " + boardType);
        System.out.println("Player Count: " + playerCount);
        System.out.println("Piece Count: " + pieceCount);
        // gameController = new GameController(gameModel, mainFrame.getGameScreen());
     });

    }

}