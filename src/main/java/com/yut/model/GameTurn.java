package com.yut.model;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.ArrayList;
import com.yut.controller.GameTurnModelInterface;   

public class GameTurn implements GameTurnModelInterface {
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

        if(type == -2)
            yut.rollYutRandomly();
        else
            yut.rollYutSelected(type);

        int result = yut.getCurrent();
        leftYuts.add(result);

        rollCount--;
        if(result == yut.YUT || result == yut.MO)rollCount++;
    }

    // 그룹을 보내면 현재 가지고 있는 윷을 사용해 이동할 수 있는 노드를 보여줌
    public Node showNextMove(Group group){

        int nextYut = leftYuts.peek();
        return group.getNextNode(nextYut);
    }

    //result == 0 업음, 1 잡음, 2 그냥 이동
    public void move(Group group, Node node){
        int result = group.move(node);
        
        if(result == 1)
            rollCount++;

        leftYuts.poll();
    }
}
