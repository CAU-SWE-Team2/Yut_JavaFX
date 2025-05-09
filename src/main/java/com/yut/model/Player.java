package com.yut.model;

import java.util.ArrayList;

public class Player {
    int id;
    static int numOfTotalPieces;
    int numOfCurrentPieces;
    ArrayList<Group> currentGroups = new ArrayList<Group>();
    Group moveTarget;

    Player(int id){
        this.id = id;
    }
    Player(int id, int numOfPieces){
        this.id = id;
        this.numOfTotalPieces = numOfPieces;
    }

    // public int throwYut(Yut yut){
    //     return yut.getRandomResult();
    // }

    // public int throwDecidedYut(Yut yut, int num){
    //     return yut.getDecidedResult(num);
    // }

    public void chooseTarget(Group target){
        this.moveTarget = target;
    }

    public Group getMoveTarget(){
        return this.moveTarget;
    }

    public int getNumOfCurrentPieces(){
        return this.numOfCurrentPieces;
    }

    public int getId(){
        return this.id;
    }

}

