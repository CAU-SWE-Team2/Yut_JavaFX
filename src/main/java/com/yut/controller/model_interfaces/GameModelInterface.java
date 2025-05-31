package com.yut.controller.model_interfaces;
import com.yut.model.Group;
import com.yut.model.Node;
import com.yut.model.Board;
import com.yut.model.Player;

public interface GameModelInterface {
    public void switchTurn();
    public boolean ifGameEnded();
    public GameTurnModelInterface getGameTurn();
    public Board getBoard();

    public Player getCurrentPlayer();
    public int getPlayerCount();
    public int getNumOfTotalPieces();
    public int getBoardType();
}
