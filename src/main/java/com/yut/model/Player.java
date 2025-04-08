package com.yut.model;

class Player {
    private final int id;
    private final int numOfTotalPieces;

    private int numOfCurrentPiece;
    private boolean isTurn = false;


    Player(int id, int pieceTotalCnt) {
        this.id = id;
        this.numOfTotalPieces = pieceTotalCnt;

    }

}
