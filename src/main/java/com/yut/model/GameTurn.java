package com.yut.model;

import java.util.ArrayDeque;
import java.util.Deque;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import com.yut.controller.model_interfaces.GameTurnModelInterface;   

public class GameTurn implements GameTurnModelInterface {

    
    
    private Deque<int[]> leftYuts;

    private Player currentPlayer;

    private int rollCount;
    private Yut yut;

    private int state;


    public GameTurn(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
        this.rollCount = 1;
        this.yut = Yut.getYut();
        leftYuts = new ArrayDeque<int[]>();

        state = GameTurnModelInterface.THROWABLE;
    }


    // type == -2면 랜덤, 아니면 type을 사용
    public void roll(int type){
        if(type == -2)
            yut.rollYutRandomly();
        else
            yut.rollYutSelected(type);

        int[] result = yut.getCurrent();

        int yut_type = result[0];

        rollCount--;

        if(yut_type == Yut.YUT || yut_type == Yut.MO){
            rollCount++;
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("One More Turn");
            alert.setHeaderText("한 번 더 던지세요!");
            alert.setContentText("윷, 모가 나와서 추가 턴을 획득했습니다.");
            alert.showAndWait();
            leftYuts.addLast(result);
        }
        else if(yut_type == Yut.BACKDO && (currentPlayer.getNumOfWaitingPieces() == currentPlayer.getNumOfCurrentPieces()) && leftYuts.isEmpty()){
            if(type == -2){
                yut.rollYutRandomly();
                result = yut.getCurrent();
            }
            else{
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Backdo");
                alert.setHeaderText("뒤로 갈 수 있는 말이 없습니다.");
                alert.setContentText("한 번 더 던지세요!");
                alert.showAndWait();
                rollCount++;
            }
        }
        else{
            leftYuts.addLast(result);
        }

         // 남은 윷이 없으면 턴 종료
        if(rollCount == 0)
            state = GameTurnModelInterface.HASTOMOVE;
    }

    // 그룹을 보내면 현재 가지고 있는 윷을 사용해 이동할 수 있는 노드를 보여줌
    public Node showNextMove(Group group){
        int nextYut = leftYuts.getFirst()[0];
        if(nextYut == Yut.BACKDO) nextYut = -1;
        return group.getNextNode(nextYut);
    }

    //result == 0 업음, 1 잡음, 2 그냥 이동
    public void move(Group group){
        int nextYut = leftYuts.getFirst()[0];
        if(nextYut == Yut.BACKDO) nextYut = -1;
        int result = group.move(group.getNextNode(nextYut));
        
        if(result == 1 && currentPlayer.getNumOfCurrentPieces() != 0){
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("One More Turn");
            alert.setHeaderText("한 번 더 던지세요!");
            alert.setContentText("다른 말을 잡았습니다.");
            alert.showAndWait();
            rollCount++;
            state = GameTurnModelInterface.THROWABLE;
        }           
        leftYuts.removeFirst();
    }

    public int getState(){
        return state;
    }
    // public void setState(int state){
    //     this.state = state;
    // }
    
    public Deque<int[]> getLeftYuts(){
        return leftYuts;
    }

    public int getRollCount(){
        return rollCount;
    }
}
