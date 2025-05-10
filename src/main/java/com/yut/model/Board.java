package com.yut.model;
import java.util.HashMap;


public class Board {
    HashMap<Integer, Node> nodes;
    Node endNode = new Node(0);
    Node waitingNode = new Node(111);


    public Node getNodeById(int id){
        return nodes.get(id);
    }
}
