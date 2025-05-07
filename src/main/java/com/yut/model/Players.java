package com.yut.model;
// 플레이어들
class Players {

    private Player[] playersArr;

    public Players(int playerCnt) {
        playersArr = new Player[playerCnt];

    }

    public Player getPlayer(int id){
        return playersArr[id];
    }

    public int size(){
        return playersArr.length;
    }
}

