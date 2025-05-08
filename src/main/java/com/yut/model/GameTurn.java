package com.yut.model;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.ArrayList;

public class GameTurn {
    private Queue<Integer> leftYuts;

    private Player currentPlayer;

    private int rollCount;
    private Yut yut;

    public GameTurn(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
        this.rollCount = 1;
        this.yut = Yut.getYut();
        leftYuts = new ArrayDeque<Integer>();
    }

    public void roll(){

        yut.rollYut();
        int result = yut.getCurrent();
        leftYuts.add(result);

        rollCount--;
        if(result == yut.YUT || result == yut.MO)rollCount++;
    }

    public ArrayList<Node> showNextMove(Group group){

        int nextYut = leftYuts.peek();

        return currentPlayer.getNextNode(group, nextYut);


    }

    public void move(Group group, Node node){
        currentPlayer.move(group, node);
        leftYuts.poll();
    }



    public Player getCurrentPlayer() {
        return currentPlayer;
    }
}
