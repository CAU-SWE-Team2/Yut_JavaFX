package com.yut.model;


class Node {
    private String id;
    private boolean canTransfer;

    Node[] toNodes;

    PieceGroup currentPieceGroup;

    public Node(String id, Node[] toNodes) {
        this.id = id;
        this.toNodes = toNodes;

        this.canTransfer = (toNodes.length != 1);

    }
}
