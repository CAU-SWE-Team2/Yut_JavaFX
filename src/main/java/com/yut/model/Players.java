package com.yut.model;
// 플레이어들
public class Players {

    private Player[] playersArr;

    public Players(int playerCnt, int numOfTotalPieces) {

        playersArr = new Player[playerCnt + 1];
        for(int i = 1; i <= playerCnt; i++){
            playersArr[i] = new Player(i, numOfTotalPieces);
        }            

    }

    public Player getPlayer(int id){
        return playersArr[id];
    }

    public Player getFirstPlayer(){
        return playersArr[1];
    }

    public int size(){
        return playersArr.length - 1;
    }
}

