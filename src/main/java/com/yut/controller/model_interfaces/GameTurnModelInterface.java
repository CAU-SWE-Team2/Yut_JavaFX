package com.yut.controller.model_interfaces;
import java.util.Deque;

import com.yut.model.Group;
import com.yut.model.Node;  



public interface GameTurnModelInterface {
    static final int THROWABLE = 1;
    static final int HASTOMOVE = 2;
    
    // type == -2면 랜덤, 아니면 type을 사용
    public void roll(int type);
    public Node showNextMove(Group group);
    public void move(Group group);
    // public void setState(int state);
    public int getState();

    public Deque<Integer> getLeftYuts();
    public int getRollCount();

}
