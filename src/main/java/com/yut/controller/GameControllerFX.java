package com.yut.controller;

import com.yut.controller.model_interfaces.GameModelInterface;
import com.yut.controller.model_interfaces.GameTurnModelInterface;
import com.yut.controller.view_interfaces.GameScreenInterface;
import com.yut.model.Group;
import com.yut.model.Node;
import com.yut.model.Player;
import com.yut.model.Yut;
import com.yut.ui.javaFX.GameScreenFX;
import com.yut.ui.javaFX.StartScreenFX;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;

public class GameControllerFX {

    private GameModelInterface gameModel;
    private GameScreenInterface gameScreen;

    public GameControllerFX(GameModelInterface gameModel, GameScreenInterface gameScreen) {
        this.gameModel = gameModel;
        this.gameScreen = gameScreen;

        gameScreen.getNodeMap().forEach((nodeId, node) -> {
            gameScreen.addNodeClickListener(node, new NodeClickListener(nodeId));
        });

        gameScreen.addRandomThrowButtonListener(new RandomThrowButtonListener());

        for (int i = 0; i < 6; i++)
            gameScreen.addSelectedThrowButtonListener(i, new SelectThrowButtonListener(i));

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
        private final int type;

        public SelectThrowButtonListener(int type) {
            this.type = type;
        }

        @Override
        public void handle(ActionEvent e) {
            GameTurnModelInterface gameTurnModel = gameModel.getGameTurn();
            if (gameTurnModel.getState() == GameTurnModelInterface.HASTOMOVE)
                return;

            gameTurnModel.roll(type);
            gameScreen.updateRandomResult(gameTurnModel.getLeftYuts().getLast());
            gameScreen.printDeckContents(gameTurnModel.getLeftYuts());
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
            if(gameTurnModel.getLeftYuts().size() == 1 && gameTurnModel.getLeftYuts().getFirst() == Yut.BACKDO){
                gameTurnModel.getLeftYuts().removeFirst();
                gameModel.switchTurn();
                gameScreen.highlightCurrentPlayer(gameModel.getCurrentPlayer().getId());
            }

            

            if(currentPlayer.getNumOfCurrentPieces() == 0){
                
            }
            
        }
    }
}
