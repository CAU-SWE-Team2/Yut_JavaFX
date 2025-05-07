package com.yut.model;
import java.util.HashMap;

// 싱글톤 보드
class Board {
    HashMap<Integer, Node> nodes;
    Node endNode = new Node(0);
    Node waitingNode = new Node(111);
}
