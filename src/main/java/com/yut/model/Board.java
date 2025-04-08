package com.yut.model;


// 싱글톤 보드
class Board {
    public static final int DEFAULT = 1;
    public static final int PENTAGON = 2;
    public static final int HEXAGON = 3;

    static Node[] nodeArr;

    static Board board = new Board();
    private Board() {}

    static Board getBoard() {
        return board;
    }

    static void createBoard(int type){

    }
}
