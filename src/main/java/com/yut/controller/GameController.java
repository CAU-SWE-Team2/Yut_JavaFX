package com.yut.controller;

import com.yut.controller.model_interfaces.GameModelInterface;
import com.yut.controller.model_interfaces.GameTurnModelInterface;
import com.yut.controller.view_interfaces.GameScreenInterface;
import com.yut.model.Game;
import com.yut.model.Yut;
import com.yut.model.Node;
import com.yut.model.Group;
import com.yut.model.Player;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
public class GameController {


    private GameModelInterface gameModel;

    private GameScreenInterface gameScreen;

    public GameController(GameModelInterface gameModel, GameScreenInterface gameScreen) {
        this.gameModel = gameModel;
        this.gameScreen = gameScreen;

        gameScreen.getNodeMap().forEach((nodeId, node) -> {
            gameScreen.addNodeClickListener(node, new NodeClickListener(nodeId));
        });

        gameScreen.addRandomThrowButtonListener(new RandomThrowButtonListener());

        for(int i = 0; i < 6; i++)
            gameScreen.addSelectedThrowButtonListener(i, new SelectThrowButtonListener(i));

        gameScreen.addMoveNewPieceButtonListener(new MoveNewPieceButtonListener());

        for(int i = 1; i <= gameModel.getPlayerCount(); i++){
            gameScreen.updatePlayerCanvas(i, gameModel.getNumOfTotalPieces());
        }

    }


    class NodeClickListener extends MouseAdapter {

        private int nodeId;
        
        public NodeClickListener(int nodeId) {
            this.nodeId = nodeId;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            GameTurnModelInterface gameTurnModel = gameModel.getGameTurn();

            if(gameTurnModel.getState() == GameTurnModelInterface.THROWABLE)
                return;
            
            
            Node node = gameModel.getBoard().getNodeById(nodeId);
            Player currentPlayer = gameModel.getCurrentPlayer();

            // 그룹 이동
            if(gameScreen.getNodeState(nodeId) == 1)
            {
                Group targetGroup = currentPlayer.getMoveTarget();
                

                if(targetGroup.getCurrentLocation().getId() != 111)
                    gameScreen.deletePiece(targetGroup.getCurrentLocation().getId());
               
                gameTurnModel.move(targetGroup);

                gameScreen.updatePlayerCanvas(currentPlayer.getId(), gameModel.getNumOfTotalPieces() - currentPlayer.getNumOfCurrentPieces());

                


                ///////)
                gameScreen.drawPiece(nodeId, currentPlayer.getId(), targetGroup.getNumOfPieces());
                gameScreen.deleteMovePreview();
                
                // gameScreen.select(nodeId);
                
                // gameTurnModel.
                if(gameTurnModel.getLeftYuts().isEmpty() && gameTurnModel.getRollCount() == 0)
                    gameModel.switchTurn();

            }
            // 아무것도 안함
            else if(gameScreen.getNodeState(nodeId) == 2)
            {
                // gameScreen.select(nodeId);
            }

            // 힌트 표시
            else if(gameScreen.getNodeState(nodeId) == 3)
            {
                currentPlayer.chooseTarget(node.getCurrentGroup());
                gameScreen.deleteMovePreview();

                //////

                Node toNode = gameTurnModel.showNextMove(node.getCurrentGroup());
                
                gameScreen.showMovePreview(toNode.getId(), currentPlayer.getId());


                // gameScreen.select(nodeId);
                
            }



        }
    }

    class MoveNewPieceButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            GameTurnModelInterface gameTurnModel = gameModel.getGameTurn();
            if(gameTurnModel.getState() == GameTurnModelInterface.THROWABLE)
                return;

            Player currentPlayer = gameModel.getCurrentPlayer();
            Group waitingGroup = currentPlayer.getNewGroup();


            if(waitingGroup != null)
            {
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
            if(gameTurnModel.getState() == GameTurnModelInterface.HASTOMOVE)
                return;
            
            gameTurnModel.roll(-2);
            gameScreen.updateRandomResult(gameTurnModel.getLeftYuts().getFirst());

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
            if(gameTurnModel.getState() == GameTurnModelInterface.HASTOMOVE)
                return;
            
            gameTurnModel.roll(type);
            gameScreen.updateRandomResult(gameTurnModel.getLeftYuts().getLast());
        }
    }


}
