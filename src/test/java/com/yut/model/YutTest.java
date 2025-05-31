package com.yut.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class YutTest {
    @Test
    void testGroupMove(){
        // given
        Player player1 = new Player(1, 2);

        Group group1 = new Group(1, player1);
        Group group2 = new Group(2, player1);

        Board board = new SquareBoard();

        group1.addToGame(board);
        group2.addToGame(board);

        // when
        group1.move(board.nodes.get(122)); // group1 moves to node 122

        // then
        assertEquals(group1.currentLocation, board.nodes.get(122)); // group1 should be at node 122
        assertEquals(board.nodes.get(122).currentGroup, group1); // group1 should be the current group at node 122
    }

    @Test
    void testGroupCatch(){
        // given
        Player player1 = new Player(1, 2);
        Player player2 = new Player(2, 2);

        Group group1 = new Group(1, player1);
        Group group2 = new Group(2, player1);
        Group group3 = new Group(1, player2);
        Group group4 = new Group(2, player2);

        Board board = new SquareBoard();

        group1.addToGame(board);
        group2.addToGame(board);
        group3.addToGame(board);
        group4.addToGame(board);

        group1.move(board.nodes.get(122));
        group2.move(board.nodes.get(122)); // group2 that carries back group1 is at node 122

        // when
        group3.move(board.nodes.get(122)); // group3 catches group2 at node 122

        // then
        assertEquals(player1.getNumOfWaitingPieces(), 2); // player1 should have 2 waiting pieces now(all pieces of player1)
        assertEquals(board.nodes.get(122).currentGroup, group3); // group3 should be the current group at node 122
    }

    @Test
    void testGroupCarryBack(){
        // given
        Player player1 = new Player(1, 2);

        Group group1 = new Group(1, player1);
        Group group2 = new Group(2, player1);

        Board board = new SquareBoard();

        group1.addToGame(board);
        group2.addToGame(board);

        group1.move(board.nodes.get(122)); // group1 is at node 122

        // when
        group2.move(board.nodes.get(122)); // group2 moves to the same node, it should carry back group1

        // then
        assertEquals(group2.numOfPieces, 2); // group2 should have 2 pieces now
        assertEquals(group2.currentPieces.size(), 2);
        assertEquals(board.nodes.get(122).currentGroup, group2); // group2 should be the current group at node 122
        assertEquals(player1.currentGroups.size(), 1); // player1 should have only group2 left
        assertEquals(player1.getNumOfWaitingPieces(), 0); // player1 should have no waiting pieces left
    }

    @Test
    void testGoal(){
        // given
        Player player1 = new Player(1, 2);

        Group group1 = new Group(1, player1);
        Group group2 = new Group(2, player1);

        Board board = new SquareBoard();

        group1.addToGame(board);
        group2.addToGame(board);

        group1.move(board.nodes.get(100)); // group1 is at node 100, it means it can goal-in with any result of yut

        // when
        group1.move(group1.getNextNode(3)); // result is geol, so it should goal-in

        // then
        assertEquals(group1.currentLocation, board.endNode); // group1 should be at endNode
        assertEquals(player1.getNumOfCurrentPieces(), 1); // player1 should have 1 current piece left
    }
}
