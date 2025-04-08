package com.yut.model;


// 싱글톤 플레이어들
class Players {
    private static Players instance = new Players();

    private static Player[] playersArr;

    private Players() {}

    static Players getPlayers() {
        return instance;
    }

    static void createPlayers(int playerCnt) {
        playersArr = new Player[playerCnt];
    }

    static void clearPlayers() {
        playersArr = null;
    }

}
