package com.yut.model;


public class Node {
    int id;
    Node beforeNode;
    Node[] nextNodes;
    Group currentGroup;

    Node(int id){
        this.id = id;
    }

    public Group getCurrentGroup() {
        return currentGroup;
    }

    public int getId() {
        return id;
    }
}
