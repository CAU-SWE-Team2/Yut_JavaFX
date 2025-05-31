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
        group1.move(board.nodes.get(122));

        // then
        assertEquals(group1.currentLocation, board.nodes.get(122));
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
        group2.move(board.nodes.get(122));

        // when
        group3.move(board.nodes.get(122));

        // then
        assertEquals(player1.getNumOfWaitingPieces(), 2);
        assertEquals(board.nodes.get(122).currentGroup, group3);
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

        group1.move(board.nodes.get(122));

        // when
        group2.move(board.nodes.get(122));

        // then
        assertEquals(group2.numOfPieces, 2);
        assertEquals(group2.currentPieces.size(), 2);
        assertEquals(board.nodes.get(122).currentGroup, group2);
        assertEquals(player1.currentGroups.size(), 1);
        assertEquals(player1.getNumOfWaitingPieces(), 0);
    }

    @Test
    void testGoal(){
        // given
        Player player1 = new Player(1, 2);
        Group group1 = new Group(1, player1);
        Board board = new SquareBoard();

        group1.addToGame(board);
        group1.move(board.nodes.get(100));

        // when

        // then
    }
}
