package com.yut.controller;

import java.util.Optional;

import com.yut.controller.model_interfaces.GameModelInterface;
import com.yut.controller.model_interfaces.GameTurnModelInterface;
import com.yut.controller.view_interfaces.GameScreenInterface;
import com.yut.model.Group;
import com.yut.model.Node;
import com.yut.model.Player;
import com.yut.model.Yut;
import com.yut.ui.javaFX.EndingStageFX;
import com.yut.ui.javaFX.GameScreenFX;
import com.yut.ui.javaFX.StartScreenFX;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class GameControllerFX {

    private GameModelInterface gameModel;
    private GameScreenInterface gameScreen;
    private Stage primaryStage;
    private Scene startScreenScene;
    

    public GameControllerFX(GameModelInterface gameModel, GameScreenInterface gameScreen, Stage primaryStage, Scene startScreenScene) {
        this.gameModel = gameModel;
        this.gameScreen = gameScreen;
        this.primaryStage = primaryStage;
        this.startScreenScene = startScreenScene;
        

        gameScreen.getNodeMap().forEach((nodeId, node) -> {
            gameScreen.addNodeClickListener(node, new NodeClickListener(nodeId));
        });

        gameScreen.addRandomThrowButtonListener(new RandomThrowButtonListener());
        gameScreen.addSelectedThrowButtonListener(new SelectThrowButtonListener());

        gameScreen.addMoveNewPieceButtonListener(new MoveNewPieceButtonListener());
        gameScreen.addGoalButtonListener(new GoalButtonListener());

        for (int i = 1; i <= gameModel.getPlayerCount(); i++) {
            gameScreen.updatePlayerCanvas(i, gameModel.getNumOfTotalPieces());
        }
        gameScreen.highlightCurrentPlayer(gameModel.getCurrentPlayer().getId());
    }

    class NodeClickListener implements EventHandler<MouseEvent> {
        private int nodeId;

        public NodeClickListener(int nodeId) {
            this.nodeId = nodeId;
        }

        @Override
        public void handle(MouseEvent event) {
            GameTurnModelInterface gameTurnModel = gameModel.getGameTurn();
            if (gameTurnModel.getState() == GameTurnModelInterface.THROWABLE)
                return;

            Node node = gameModel.getBoard().getNodeById(nodeId);
            Player currentPlayer = gameModel.getCurrentPlayer();
            gameScreen.setGoalButtonVisible(false);

            int state = gameScreen.getNodeState(nodeId);
            if (state == 1) { // 힌트 노드 클릭시
                Group targetGroup = currentPlayer.getMoveTarget();
                if (targetGroup.getCurrentLocation().getId() != 111)
                    gameScreen.deletePiece(targetGroup.getCurrentLocation().getId());

                gameScreen.deletePiece(nodeId);
                gameTurnModel.move(targetGroup);

                gameScreen.printDeckContents(gameTurnModel.getLeftYuts());
                gameScreen.updatePlayerCanvas(currentPlayer.getId(), currentPlayer.getNumOfCurrentPieces());
                gameScreen.drawPiece(nodeId, currentPlayer.getId(), node.getCurrentGroup().getNumOfPieces());
                gameScreen.deleteMovePreview();

                if (gameTurnModel.getLeftYuts().isEmpty() && gameTurnModel.getRollCount() == 0)
                    gameModel.switchTurn();

                gameScreen.highlightCurrentPlayer(gameModel.getCurrentPlayer().getId());

            } else if (state == 3) { // 자기 말 선택 시
                if (node.getCurrentGroup().getOwner().getId() == currentPlayer.getId()) {
                    currentPlayer.chooseTarget(node.getCurrentGroup());
                    gameScreen.deleteMovePreview();
                    Node toNode = gameTurnModel.showNextMove(node.getCurrentGroup());

                    if (toNode.getId() != 0) {
                        gameScreen.showMovePreview(toNode.getId(), currentPlayer.getId());
                    } else {
                        gameScreen.setGoalButtonVisible(true);
                    }
                }

            }
        }
    }

    class MoveNewPieceButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            GameTurnModelInterface gameTurnModel = gameModel.getGameTurn();
            if (gameTurnModel.getState() == GameTurnModelInterface.THROWABLE)
                return;

            Player currentPlayer = gameModel.getCurrentPlayer();
            Group waitingGroup = currentPlayer.getNewGroup();

            if (waitingGroup != null) {
                gameScreen.deleteMovePreview();
                currentPlayer.chooseTarget(waitingGroup);
                Node toNode = gameTurnModel.showNextMove(waitingGroup);
                gameScreen.showMovePreview(toNode.getId(), currentPlayer.getId());
            }
        }
    }

    class RandomThrowButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            GameTurnModelInterface gameTurnModel = gameModel.getGameTurn();
            if (gameTurnModel.getState() == GameTurnModelInterface.HASTOMOVE)
                return;

            gameTurnModel.roll(-2);
            gameScreen.updateRandomResult(gameTurnModel.getLeftYuts().getLast());
            gameScreen.printDeckContents(gameTurnModel.getLeftYuts());
        }
    }

    class SelectThrowButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            int[] yutResult = new int[5];

            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("지정 윷 던지기");
            alert.setHeaderText("결과를 선택하세요");
            alert.setContentText("빽도/도/개/걸/윷/모");

            ButtonType buttonTypeBack = new ButtonType("빽도");
            ButtonType buttonTypeDo = new ButtonType("도");
            ButtonType buttonTypeGae = new ButtonType("개");
            ButtonType buttonTypeGeol = new ButtonType("걸");
            ButtonType buttonTypeYut = new ButtonType("윷");
            ButtonType buttonTypeMo = new ButtonType("모");
            ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(buttonTypeBack, buttonTypeDo, buttonTypeGae, buttonTypeGeol, buttonTypeYut, buttonTypeMo, buttonTypeCancel);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonTypeBack){
                yutResult[0] = Yut.BACKDO;
                yutResult[1] = 0;
                yutResult[2] = 1;
                yutResult[3] = 1;
                yutResult[4] = 1;
            } else if (result.get() == buttonTypeDo) {
                yutResult[0] = Yut.DO;
                yutResult[1] = 1;
                yutResult[2] = 1;
                yutResult[3] = 0;
                yutResult[4] = 1;
            } else if (result.get() == buttonTypeGae) {
                yutResult[0] = Yut.GE;
                yutResult[1] = 0;
                yutResult[2] = 1;
                yutResult[3] = 1;
                yutResult[4] = 0;
            } else if (result.get() == buttonTypeGeol) {
                yutResult[0] = Yut.GUL;
                yutResult[1] = 0;
                yutResult[2] = 0;
                yutResult[3] = 0;
                yutResult[4] = 1;
            } else if (result.get() == buttonTypeYut) {
                yutResult[0] = Yut.YUT;
                yutResult[1] = 0;
                yutResult[2] = 0;
                yutResult[3] = 0;
                yutResult[4] = 0;
            } else if (result.get() == buttonTypeMo) {
                yutResult[0] = Yut.MO;
                yutResult[1] = 1;
                yutResult[2] = 1;
                yutResult[3] = 1;
                yutResult[4] = 1;
            }else {
                // ... user chose CANCEL or closed the dialog
            }
            gameModel.getGameTurn().roll(yutResult[0]);

            gameScreen.updateRandomResult(gameModel.getGameTurn().getLeftYuts().getLast());
            gameScreen.printDeckContents(gameModel.getGameTurn().getLeftYuts());
        }
    }

    class GoalButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            GameTurnModelInterface gameTurnModel = gameModel.getGameTurn();
            if (gameTurnModel.getState() == GameTurnModelInterface.THROWABLE)
                return;

            Player currentPlayer = gameModel.getCurrentPlayer();
            Group targetGroup = currentPlayer.getMoveTarget();

            gameScreen.deletePiece(targetGroup.getCurrentLocation().getId());
            gameTurnModel.move(targetGroup);
            gameScreen.printDeckContents(gameTurnModel.getLeftYuts());
            targetGroup.goal();
            
            gameScreen.updatePlayerCanvas(currentPlayer.getId(), gameModel.getCurrentPlayer().getNumOfCurrentPieces());
            gameScreen.setGoalButtonVisible(false);

            if (gameTurnModel.getLeftYuts().isEmpty() && gameTurnModel.getRollCount() == 0){
                gameModel.switchTurn();
                gameScreen.highlightCurrentPlayer(gameModel.getCurrentPlayer().getId());
            }
            if(gameTurnModel.getLeftYuts().size() == 1 && gameTurnModel.getLeftYuts().getFirst()[0] == Yut.BACKDO){
                gameTurnModel.getLeftYuts().removeFirst();
                gameModel.switchTurn();
                gameScreen.highlightCurrentPlayer(gameModel.getCurrentPlayer().getId());
            }

            

            if(currentPlayer.getNumOfCurrentPieces() == 0){
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("우승!");
                alert.setHeaderText("우승자는 플레이어 " + currentPlayer.getId() + " 입니다!");
                
                ButtonType buttonRestart = new ButtonType("재시작");
                ButtonType buttonEnd = new ButtonType("종료");
                
                alert.getButtonTypes().setAll(buttonRestart, buttonEnd);
                
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == buttonRestart){
                    primaryStage.setScene(startScreenScene);
                } else 
                {
                    primaryStage.close();
                }
            }
            
        }
    }
}
