package com.yut.controller;
import com.yut.model.Group;
import com.yut.model.Node;  



public interface GameTurnModelInterface {

    public void roll(int type);
    public Node showNextMove(Group group);
    public void move(Group group, Node node);

}
