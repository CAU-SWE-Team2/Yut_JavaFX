package com.yut.controller;

public class TitleController{
    
    private int playerNumber;
    private int boardType;
    private int numOfTotalPieces;

    public void PlayerNumberSelect(int playerNumber){
        TitleViewInterface titleView = new TitleView();

        titleView.PlayerNumber
        this.playerNumber = playerNumber;
        
    }
    void BoardTypeSelect(int boardType){
        this.boardType = boardType;
    }
    void GameStart();
}
