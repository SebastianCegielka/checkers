package com.github.sebastiancegielka.checkers.model;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.*;

public class BoardTest {

    @Test
    public void shouldContainPawnsAtGivenKnownFieldsWhenNewBoardGenerated(){
        //given
        Board board = new Board();

        //when
        board.generateNewBoard();

        //then
        assertThat(board.getField(7,0)).isEqualByComparingTo(Color.R);
        assertThat(board.getField(2,7)).isEqualByComparingTo(Color.W);
        assertThat(board.getField(1,4)).isEqualByComparingTo(Color.W);
        assertThat(board.getField(6,5)).isEqualByComparingTo(Color.R);
    }

    @Test
    public void shouldntChangeDataInPrimordialArrayWhenMakingChangeInDeepCopiedArray(){
        //given
        Board board = new Board();
        board.generateNewBoard();
        Color[][] copy = board.deepCopy();

        //when
        copy[7][0] = Color.Rk;

        //then
        assertThat(board.getField(7,6)).isEqualByComparingTo(copy[7][6]);
        assertThat(board.getField(7,0)).isNotEqualByComparingTo(copy[7][0]);
    }
}