package com.yut.controller.view_interfaces;

import com.yut.ui.swing.ClickableNode;

import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.Deque;
import java.util.Map;

public interface GameScreenInterface {
    // public void select(int nodeID);
    public void showMovePreview(int nodeID, int playerID);

    public void deleteMovePreview();

    public void drawPiece(int nodeID, int playerID, int pieceNumber);

    public void deletePiece(int nodeID);

    public void updatePlayerCanvas(int playerID, int pieceCount);

    void highlightCurrentPlayer(int playerId);

    public void select(int nodeID);

    public void updateRandomResult(int yut);

    public void printDeckContents(Deque<Integer> deck);
    
    // 힌트가 있는 노드인지 = 1, 빈 노드인지 = 2, 말이 있는 노드인지 = 3;
    public int getNodeState(int nodeID);

    public void addRandomThrowButtonListener(ActionListener listener);

    public void addSelectedThrowButtonListener(int index, ActionListener listener);

    public void addBackButtonListener(ActionListener listener);

    public void addNodeClickListener(ClickableNode node, MouseListener listener);
    public void addMoveNewPieceButtonListener(ActionListener listener); 
    public void addGoalButtonListener(ActionListener listener);
    public void setGoalButtonVisible(boolean visible);


    public Map<Integer, ClickableNode> getNodeMap();
}
