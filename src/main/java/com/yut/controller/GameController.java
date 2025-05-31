package com.yut.controller;

import com.yut.Main;
import com.yut.controller.model_interfaces.GameModelInterface;
import com.yut.controller.model_interfaces.GameTurnModelInterface;
import com.yut.controller.view_interfaces.GameScreenInterface;
import com.yut.model.Node;
import com.yut.model.Game;
import com.yut.model.Group;
import com.yut.model.Player;
import com.yut.model.Yut;
import com.yut.ui.swing.EndingFrame;
import com.yut.ui.swing.GameScreen;
import com.yut.ui.swing.MainFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameController {

    private GameModelInterface gameModel;

    private GameScreen gameScreen;

    public GameController(GameModelInterface gameModel, GameScreen gameScreen) {
        this.gameModel = gameModel;
        this.gameScreen = gameScreen;

        gameScreen.getNodeMap().forEach((nodeId, node) -> {
            gameScreen.addNodeClickListener(node, new NodeClickListener(nodeId));
        });

        gameScreen.addRandomThrowButtonListener(new RandomThrowButtonListener());

        for (int i = 0; i < 6; i++)
            gameScreen.addSelectedThrowButtonListener(i, new SelectThrowButtonListener(i));

        gameScreen.addMoveNewPieceButtonListener(new MoveNewPieceButtonListener());

        for (int i = 1; i <= gameModel.getPlayerCount(); i++) {
            gameScreen.updatePlayerCanvas(i, gameModel.getNumOfTotalPieces());
        }
        gameScreen.highlightCurrentPlayer(gameModel.getCurrentPlayer().getId());
        gameScreen.addGoalButtonListener(new GoalButtonListener());

    }

    class NodeClickListener extends MouseAdapter {
        private int nodeId;

        public NodeClickListener(int nodeId) {
            this.nodeId = nodeId;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            GameTurnModelInterface gameTurnModel = gameModel.getGameTurn();

            if (gameTurnModel.getState() == GameTurnModelInterface.THROWABLE)
                return;

            Node node = gameModel.getBoard().getNodeById(nodeId);
            Player currentPlayer = gameModel.getCurrentPlayer();


            gameScreen.setGoalButtonVisible(false);

            // 그룹 이동
            if (gameScreen.getNodeState(nodeId) == 1) {
                Group targetGroup = currentPlayer.getMoveTarget();

                if (targetGroup.getCurrentLocation().getId() != 111)
                    gameScreen.deletePiece(targetGroup.getCurrentLocation().getId());
               
                gameScreen.deletePiece(nodeId);
                gameTurnModel.move(targetGroup);
                gameScreen.printDeckContents(gameTurnModel.getLeftYuts());

                gameScreen.updatePlayerCanvas(currentPlayer.getId(), gameModel.getCurrentPlayer().getNumOfCurrentPieces());

                


                ///////)
                gameScreen.drawPiece(nodeId, currentPlayer.getId(), node.getCurrentGroup().getNumOfPieces());
                gameScreen.deleteMovePreview();

                // gameScreen.select(nodeId);

                // gameTurnModel.
                if (gameTurnModel.getLeftYuts().isEmpty() && gameTurnModel.getRollCount() == 0)
                    gameModel.switchTurn();
                Player newPlayer = gameModel.getCurrentPlayer();
                gameScreen.highlightCurrentPlayer(newPlayer.getId());

            }
            // 아무것도 안함
            else if (gameScreen.getNodeState(nodeId) == 2) {
                // gameScreen.select(nodeId);
            }

            // 힌트 표시
            else if (gameScreen.getNodeState(nodeId) == 3) {
                if(node.getCurrentGroup().getOwner().getId() == currentPlayer.getId()){
                    currentPlayer.chooseTarget(node.getCurrentGroup());
                    gameScreen.deleteMovePreview();
                    Node toNode = gameTurnModel.showNextMove(node.getCurrentGroup());
                
                    if(toNode.getId() != 0)
                        gameScreen.showMovePreview(toNode.getId(), currentPlayer.getId());
                    else{
                        gameScreen.setGoalButtonVisible(true);
                    }
                }

                // gameScreen.select(nodeId);

            }

        }
    }

    class MoveNewPieceButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
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

    class RandomThrowButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            GameTurnModelInterface gameTurnModel = gameModel.getGameTurn();
            if (gameTurnModel.getState() == GameTurnModelInterface.HASTOMOVE)
                return;

            gameTurnModel.roll(-2);
            int result = gameTurnModel.getLeftYuts().getLast();
            
            gameScreen.updateRandomResult(result);
            gameScreen.printDeckContents(gameTurnModel.getLeftYuts());
        }
    }

    class SelectThrowButtonListener implements ActionListener {
        private int type;

        public SelectThrowButtonListener(int type) {
            this.type = type;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            GameTurnModelInterface gameTurnModel = gameModel.getGameTurn();
            if (gameTurnModel.getState() == GameTurnModelInterface.HASTOMOVE)
                return;

            gameTurnModel.roll(type);

            gameScreen.updateRandomResult(gameTurnModel.getLeftYuts().getLast());
            gameScreen.printDeckContents(gameTurnModel.getLeftYuts());
        }
    }


    class GoalButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            GameTurnModelInterface gameTurnModel = gameModel.getGameTurn();
            if(gameTurnModel.getState() == GameTurnModelInterface.THROWABLE)
                return;
                
            
            Player currentPlayer = gameModel.getCurrentPlayer();

            Group targetGroup = currentPlayer.getMoveTarget();
            gameScreen.deletePiece(targetGroup.getCurrentLocation().getId());
            gameTurnModel.move(targetGroup);

            gameScreen.printDeckContents(gameTurnModel.getLeftYuts());
            targetGroup.goal();

            if (gameTurnModel.getLeftYuts().isEmpty() && gameTurnModel.getRollCount() == 0){
                gameModel.switchTurn();
                gameScreen.highlightCurrentPlayer(gameModel.getCurrentPlayer().getId());
            }
            if(gameTurnModel.getLeftYuts().size() == 1 && gameTurnModel.getLeftYuts().getFirst() == Yut.BACKDO){
                gameTurnModel.getLeftYuts().removeFirst();
                gameModel.switchTurn();
                gameScreen.highlightCurrentPlayer(gameModel.getCurrentPlayer().getId());
            }

            gameScreen.updatePlayerCanvas(currentPlayer.getId(), gameModel.getCurrentPlayer().getNumOfCurrentPieces());
            gameScreen.setGoalButtonVisible(false);

            if(currentPlayer.getNumOfCurrentPieces() == 0){
                EndingFrame endingFrame = new EndingFrame(currentPlayer.getId());
                endingFrame.setVisible(true);
                gameScreen.setVisible(false);
            }
        }
    }
}
