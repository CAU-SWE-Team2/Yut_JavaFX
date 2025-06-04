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
        Player.numOfTotalPieces = numOfPieces;
        this.numOfCurrentPieces = numOfPieces;
    }

    public Group getNewGroup(){
        Group newGroup = null;
        for(int i = 0; i < currentGroups.size(); i++){
            if(currentGroups.get(i).currentLocation.id == 111){
                newGroup = currentGroups.get(i);
                break;
            }
        }

        return newGroup;
    }

    public void chooseTarget(Group target){
        if(target.getOwner().getId() == this.id){
            this.moveTarget = target;
        }
    }

    public Group getMoveTarget(){
        return this.moveTarget;
    }
    public void setMoveTarget(Group target){
        this.moveTarget = target;
    }

    public int getNumOfCurrentPieces(){
        return this.numOfCurrentPieces;
    }

    public int getId(){
        return this.id;
    }

    public int getNumOfWaitingPieces(){
        int num = 0;
        for(int i = 0; i < currentGroups.size(); i++){
            if(currentGroups.get(i).currentLocation.id == 111){
                num++;
            }
        }
        return num;
    }

}

