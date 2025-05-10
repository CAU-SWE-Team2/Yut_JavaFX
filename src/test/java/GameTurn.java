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


    // type == -2면 랜덤, 아니면 type을 사용
    public void roll(int type){

        if(type == 0)
            yut.rollYutRandomly();
        else
            yut.rollYutSelected(type);

        int result = yut.getCurrent();
        leftYuts.add(result);

        rollCount--;
        if(result == yut.YUT || result == yut.MO)rollCount++;
    }

    public Node showNextMove(Group group){

        int nextYut = leftYuts.peek();
        return group.getNextNode(nextYut);
    }

    public void move(Group group, Node node){
        group.move(node);
        
        leftYuts.poll();
    }



    public Player getCurrentPlayer() {
        return currentPlayer;
    }
}
