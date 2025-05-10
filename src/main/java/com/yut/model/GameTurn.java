package com.yut.model;

import java.util.ArrayDeque;
import java.util.Deque;

import javax.swing.JOptionPane;

import com.yut.controller.model_interfaces.GameTurnModelInterface;   

public class GameTurn implements GameTurnModelInterface {

    
    
    private Deque<Integer> leftYuts;

    private Player currentPlayer;

    private int rollCount;
    private Yut yut;

    private int state;


    public GameTurn(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
        this.rollCount = 1;
        this.yut = Yut.getYut();
        leftYuts = new ArrayDeque<Integer>();

        state = GameTurnModelInterface.THROWABLE;
    }


    // type == -2면 랜덤, 아니면 type을 사용
    public void roll(int type){
        if(type == -2)
            yut.rollYutRandomly();
        else
            yut.rollYutSelected(type);

        int result = yut.getCurrent();
        leftYuts.addLast(result);

        rollCount--;

        if(result == Yut.YUT || result == Yut.MO){
            JOptionPane.showMessageDialog(null, "한 번 더 던지세요!", "추가 턴", JOptionPane.INFORMATION_MESSAGE);
            rollCount++;
        }
        if(rollCount == 0)
            state = GameTurnModelInterface.HASTOMOVE;
    }

    // 그룹을 보내면 현재 가지고 있는 윷을 사용해 이동할 수 있는 노드를 보여줌
    public Node showNextMove(Group group){
        int nextYut = leftYuts.getFirst();
        if(nextYut == Yut.BACKDO) nextYut = -1;
        return group.getNextNode(nextYut);
    }

    //result == 0 업음, 1 잡음, 2 그냥 이동
    public void move(Group group){
        int nextYut = leftYuts.getFirst();
        if(nextYut == Yut.BACKDO) nextYut = -1;
        int result = group.move(group.getNextNode(nextYut));
        
        if(result == 1){
            JOptionPane.showMessageDialog(null, "한 번 더 던지세요!", "추가 턴", JOptionPane.INFORMATION_MESSAGE);
            rollCount++;
            state = GameTurnModelInterface.THROWABLE;
        }             
        leftYuts.removeFirst();

    }

    public int getState(){
        return state;
    }
    // public void setState(int state){
    //     this.state = state;
    // }
    
    public Deque<Integer> getLeftYuts(){
        return leftYuts;
    }

    public int getRollCount(){
        return rollCount;
    }
}
