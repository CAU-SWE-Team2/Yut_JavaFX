package com.yut.model;
// 플레이어들
public class Players {

    private Player[] playersArr;

    public Players(int playerCnt, int numOfTotalPieces) {

        playersArr = new Player[playerCnt];
        for(int i = 0; i < playerCnt; i++){
            playersArr[i] = new Player(i + 1, numOfTotalPieces);
        }            

    }

    public Player getPlayer(int id){
        return playersArr[id];
    }

    public Player getFirstPlayer(){
        return playersArr[0];
    }

    public int size(){
        return playersArr.length;
    }
}

