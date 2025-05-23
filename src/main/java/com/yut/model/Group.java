package com.yut.model;

import java.util.ArrayList;


public class Group {
    int id;
    int numOfPieces;
    ArrayList<Integer> currentPieces = new ArrayList<>();
    
    Node currentLocation;
    Player owner;
    Board playingBoard;

    Group(int id, Player owner){
        this.id = id;
        this.currentPieces.add(id);
        this.owner = owner;
        this.owner.currentGroups.add(this);
        this.numOfPieces = 1;
    }

    private void carryBack(Group existingGroup){
        this.currentPieces.addAll(existingGroup.currentPieces);
        this.numOfPieces += existingGroup.numOfPieces;
        
        existingGroup.currentPieces.clear();
        existingGroup.owner.currentGroups.remove(existingGroup);

        this.currentLocation.currentGroup = this;
        existingGroup.currentLocation = null;
    }

    private void catchOtherGroup(Group existingGroup){
        int size = existingGroup.currentPieces.size();
        for(int i = 0; i < size; i++){
            Group repairGroup = new Group(existingGroup.currentPieces.get(i), existingGroup.owner);
            repairGroup.currentLocation = playingBoard.waitingNode;
            existingGroup.owner.currentGroups.add(repairGroup);
        }
        existingGroup.owner.currentGroups.remove(existingGroup);
        existingGroup.currentLocation = null;
        this.currentLocation.currentGroup = this;
    }

    public void addToGame(Board board){
        this.currentLocation = board.waitingNode;
        this.playingBoard = board;
    }

    public int move(Node node){
        this.currentLocation.currentGroup = null;
        this.currentLocation = node;
        
        // if(node.equals(playingBoard.endNode)){
        //     this.owner.numOfCurrentPieces -= this.numOfPieces;
        //     this.owner.currentGroups.remove(this);
        //     return 2;
        // }
        // else{
            if(node.currentGroup != null){
                if(node.currentGroup.owner == this.owner){
                    this.carryBack(node.currentGroup);
                    return 0;
                }
                else if(this.currentLocation.getId() != 0){
                    this.catchOtherGroup(node.currentGroup);
                    return 1;
                }
                else{
                    return 2;
                }
            }
            else {
                node.currentGroup = this;
                return 2;
            }
        // }
    }

    public Node getNextNode(int n){
        Node nextNode = this.currentLocation;
        if(n == -1){
            nextNode = nextNode.beforeNode;
        }
        else {
            int whereToMove = nextNode.id/100-1;
            for (int i = 0; i < n; i++) {
                nextNode = nextNode.nextNodes[whereToMove];
            }
        }
        return nextNode;
    }

    public void goal(){
        // if(this.currentLocation.id == 0){
            this.owner.numOfCurrentPieces -= this.numOfPieces;
            this.owner.currentGroups.remove(this);
        // }
    }

    public int getNumOfPieces(){
        return this.numOfPieces;
    }

    public Node getCurrentLocation(){
        return this.currentLocation;
    }

    public Player getOwner(){
        return this.owner;
    }
}
