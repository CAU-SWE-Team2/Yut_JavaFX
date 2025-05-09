package com.yut.model;
import com.yut.controller.model_interfaces.GameModelInterface;

public class Game implements GameModelInterface {

    private Players players;
    private Board board;

    private GameTurn gameTurn;
    private int currentPlayerId;
    private int playerCount;


    public Game(int boardType, int playerCount, int numOfTotalPieces) {
        this.playerCount = playerCount;
        players = new Players(playerCount, numOfTotalPieces);
        
        if(boardType == 0)
            board = new SquareBoard();
        else if(boardType == 1)
            board = new PentagonBoard();
        else
            board = new HexagonBoard();
        

        currentPlayerId = players.getFirstPlayer().id;
        gameTurn = new GameTurn(players.getPlayer(0));



        for(int i = 0; i < players.size(); i++){
            Player player = players.getPlayer(i);
            for(int j = 1; j <= numOfTotalPieces; j++ ){
                Group group = new Group(j, player);
                group.addToGame(board);
                  
            }
        
        }
    }

    public void switchTurn(){
        currentPlayerId++;
        Player player = players.getPlayer((currentPlayerId) % players.size() + 1);

        gameTurn = new GameTurn(player);
    }

    public boolean ifGameEnded(){
        for (int i = 0; i < players.size(); i++){
            Player player = players.getPlayer(i);
            if(player.getNumOfCurrentPieces() == 0)
                return true;
        }
        return false;
    }

    public GameTurn getGameTurn(){
        return gameTurn;
    }

    public Board getBoard(){
        return board;
    }

    public Player getCurrentPlayer(){
        return players.getPlayer(currentPlayerId);
    }

    public int getPlayerCount(){
        return playerCount;
    }
}

