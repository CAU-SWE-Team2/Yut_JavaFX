package com.yut.model;
import java.util.HashMap;


class Board {
    HashMap<Integer, Node> nodes;
    Node endNode = new Node(0);
    Node waitingNode = new Node(111);
}
