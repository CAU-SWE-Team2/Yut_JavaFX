package com.yut.model;

public class Game {

    private Players players;
    private Board board;

    private GameTurn gameTurn;
    private int currentPlayerId;
    public Game(int boardType, int playerCount) {
        players = new Players(playerCount);
        board = new Board(boardType);

        currentPlayerId = 0;
        gameTurn = new GameTurn(players.getPlayer(0));
    }

    public void switchTurn(){
        currentPlayerId++;
        Player player = players.getPlayer((currentPlayerId) % players.size());

        gameTurn = new GameTurn(player);
    }




}
