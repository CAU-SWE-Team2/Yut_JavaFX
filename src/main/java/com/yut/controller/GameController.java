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
    private GameTurnModelInterface gameTurnModel;

    private GameScreenInterface gameScreen;

    public GameController(GameModelInterface gameModel, GameScreenInterface gameScreen) {
        this.gameModel = gameModel;
        this.gameTurnModel = gameModel.getGameTurn();
        this.gameScreen = gameScreen;

        gameScreen.getNodeMap().forEach((nodeId, node) -> {
            gameScreen.addNodeClickListener(node, new NodeClickListener(nodeId));
        });

        gameScreen.addRandomThrowButtonListener(new RandomThrowButtonListener());

        for(int i = 0; i < 5; i++)
            gameScreen.addSelectedThrowButtonListener(i, new SelectThrowButtonListener(i - 1));



        for(int i = 1; i <= gameModel.getPlayerCount(); i++){
            gameScreen.updatePlayerCanvas(i, gameModel.getNumOfTotalPieces());
            

            // for(int j = 1; j <= gameModel.getNumOfTotalPieces(); j++){
            gameScreen.drawPiece(100, i, 1);
        }

    }


    class NodeClickListener extends MouseAdapter {

        private int nodeId;
        
        public NodeClickListener(int nodeId) {
            this.nodeId = nodeId;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if(gameTurnModel.getState() == GameTurnModelInterface.THROWABLE)
                return;
            
            
            Node node = gameModel.getBoard().getNodeById(nodeId);
            Player currentPlayer = gameModel.getCurrentPlayer();

            // 그룹 이동
            if(gameScreen.getNodeState(nodeId) == 1)
            {
                Group targetGroup = currentPlayer.getMoveTarget();
                

                gameScreen.deletePiece(targetGroup.getCurrentLocation().getId());
               
                gameTurnModel.move(targetGroup, node);

                gameScreen.updatePlayerCanvas(currentPlayer.getId(), currentPlayer.getNumOfCurrentPieces());

                if(gameTurnModel.getLeftYuts().isEmpty())
                    gameModel.switchTurn();


                ///////)
                gameScreen.drawPiece(nodeId, currentPlayer.getId(), targetGroup.getNumOfPieces());
                // gameScreen.select(nodeId);


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

                //////

                Node toNode = gameTurnModel.showNextMove(node.getCurrentGroup());
                
                gameScreen.showMovePreview(toNode.getId(), currentPlayer.getId());


                // gameScreen.select(nodeId);
                
            }



        }
    }

    class RandomThrowButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(gameTurnModel.getState() == GameTurnModelInterface.HASTOMOVE)
                return;
            
            gameTurnModel.roll(-2);

        }
    }

    class SelectThrowButtonListener implements ActionListener {

        private int type;

        public SelectThrowButtonListener(int type) {
            this.type = type;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(gameTurnModel.getState() == GameTurnModelInterface.HASTOMOVE)
                return;
            
            gameTurnModel.roll(type);
        }
    }


}
