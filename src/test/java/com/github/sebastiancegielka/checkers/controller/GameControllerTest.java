package com.github.sebastiancegielka.checkers.controller;

import com.github.sebastiancegielka.checkers.model.Board;
import com.github.sebastiancegielka.checkers.model.Color;
import com.github.sebastiancegielka.checkers.model.Move;
import org.junit.Test;
import static org.assertj.core.api.Assertions.*;

public class GameControllerTest {

    @Test
    public void shouldMoveDiagonalByOneFieldWhenNormalPawnChosenAndMoveGiven(){
        //given
        Board board = new Board();
        GameController controller = new GameController(board);
        board.generateEmptyBoard();
        board.setField(5,2,Color.R);
        Move move = Move.Builder.create()
                .withPawnColor(Color.R)
                .withStartingRow(5)
                .withStartingColumn(2)
                .withTargetRow(4)
                .withTargetColumn(1)
                .build();

        //when
        controller.move(move);

        //then
        assertThat(board.getField(4,1)).isEqualByComparingTo(Color.R);
        assertThat(board.getField(5,2)).isEqualByComparingTo(Color.o);
    }

    @Test
    public void shouldNotMoveWhenGivenTwoFieldsAwayTarget(){
        //given
        Board board = new Board();
        GameController controller = new GameController(board);
        board.generateEmptyBoard();
        board.setField(5,2,Color.R);
        Move move = Move.Builder.create()
                .withPawnColor(Color.R)
                .withStartingRow(5)
                .withStartingColumn(2)
                .withTargetRow(3)
                .withTargetColumn(0)
                .build();

        //when
        controller.move(move);

        //then
        assertThat(board.getField(3,0)).isNotEqualByComparingTo(Color.R);
        assertThat(board.getField(5,2)).isEqualByComparingTo(Color.R);
    }

    @Test
    public void shouldNotMoveNotDiagonallyWhenSuchCommandGiven(){
        //given
        Board board = new Board();
        GameController controller = new GameController(board);
        board.generateEmptyBoard();
        board.setField(5,2,Color.R);
        Move move = Move.Builder.create()
                .withPawnColor(Color.R)
                .withStartingRow(5)
                .withStartingColumn(2)
                .withTargetRow(4)
                .withTargetColumn(2)
                .build();

        //when
        controller.move(move);

        //then
        assertThat(board.getField(4,2)).isNotEqualByComparingTo(Color.R);
        assertThat(board.getField(5,2)).isEqualByComparingTo(Color.R);
    }

    @Test
    public void shouldCaptureOppositePawnWhenOnFieldNextToItWhileFieldBehindItIsEmpty(){
        //given
        Board board = new Board();
        GameController controller = new GameController(board);
        board.generateEmptyBoard();
        board.setField(5,2, Color.R);
        board.setField(4,3, Color.W);
        Move move = Move.Builder.create()
                .withPawnColor(Color.R)
                .withStartingRow(5)
                .withStartingColumn(2)
                .withTargetRow(3)
                .withTargetColumn(4)
                .build();

        //when
        controller.move(move);

        //then
        assertThat(board.getField(5,2)).isNotEqualByComparingTo(Color.R);
        assertThat(board.getField(4,3)).isNotEqualByComparingTo(Color.W);
        assertThat(board.getField(3,4)).isEqualByComparingTo(Color.R);
    }

    @Test
    public void shouldNotCaptureSamePawnWhenOnFieldNextToItWhileFieldBehindItIsEmpty(){
        //given
        Board board = new Board();
        GameController controller = new GameController(board);
        board.generateEmptyBoard();
        board.setField(5,2, Color.R);
        board.setField(4,3, Color.R);
        Move move = Move.Builder.create()
                .withPawnColor(Color.R)
                .withStartingRow(5)
                .withStartingColumn(2)
                .withTargetRow(3)
                .withTargetColumn(4)
                .build();

        //when
        controller.move(move);

        //then
        assertThat(board.getField(5,2)).isEqualByComparingTo(Color.R);
        assertThat(board.getField(4,3)).isEqualByComparingTo(Color.R);
        assertThat(board.getField(3,4)).isNotEqualByComparingTo(Color.R);
    }

    @Test
    public void shouldNotCaptureOppositePawnWhenOnFieldNextToItWhileFieldBehindItIsNotEmpty(){
        //given
        Board board = new Board();
        GameController controller = new GameController(board);
        board.generateEmptyBoard();
        board.setField(5,2, Color.R);
        board.setField(4,3, Color.W);
        board.setField(3,4, Color.W);
        Move move = Move.Builder.create()
                .withPawnColor(Color.R)
                .withStartingRow(5)
                .withStartingColumn(2)
                .withTargetRow(3)
                .withTargetColumn(4)
                .build();

        //when
        controller.move(move);

        //then
        assertThat(board.getField(5,2)).isEqualByComparingTo(Color.R);
        assertThat(board.getField(4,3)).isEqualByComparingTo(Color.W);
        assertThat(board.getField(3,4)).isNotEqualByComparingTo(Color.R);
    }

    @Test
    public void shouldMoveDiagonallyThroughTheWholeBoardWhenNoPawnsAreLocatedOnTheLineOfMoveIfThePawnIsAKing(){
        //given
        Board board = new Board();
        GameController controller = new GameController(board);
        board.generateEmptyBoard();
        board.setField(7,0, Color.Rk);
        Move move = Move.Builder.create()
                .withPawnColor(Color.Rk)
                .withStartingRow(7)
                .withStartingColumn(0)
                .withTargetRow(0)
                .withTargetColumn(7)
                .build();

        //when
        controller.move(move);

        //then
        assertThat(board.getField(7,0)).isNotEqualByComparingTo(Color.Rk);
        assertThat(board.getField(0,7)).isEqualByComparingTo(Color.Rk);
    }

    @Test
    public void shouldNotMoveOtherwiseThanDiagonallyIfPawnIsAKing(){
        //given
        Board board = new Board();
        GameController controller = new GameController(board);
        board.generateEmptyBoard();
        board.setField(7,0, Color.Rk);
        Move move = Move.Builder.create()
                .withPawnColor(Color.Rk)
                .withStartingRow(7)
                .withStartingColumn(0)
                .withTargetRow(0)
                .withTargetColumn(6)
                .build();

        //when
        controller.move(move);

        //then
        assertThat(board.getField(7,0)).isEqualByComparingTo(Color.Rk);
        assertThat(board.getField(0,6)).isNotEqualByComparingTo(Color.Rk);
    }

    @Test
    public void shouldCaptureSingleOppositePawnWhenItsLocatedOnKingsLineOfMove(){
        //given
        Board board = new Board();
        GameController controller = new GameController(board);
        board.generateEmptyBoard();
        board.setField(7,0, Color.Rk);
        board.setField(4,3, Color.W);
        Move move = Move.Builder.create()
                .withPawnColor(Color.Rk)
                .withStartingRow(7)
                .withStartingColumn(0)
                .withTargetRow(0)
                .withTargetColumn(7)
                .build();

        //when
        controller.move(move);

        //then
        assertThat(board.getField(7,0)).isNotEqualByComparingTo(Color.Rk);
        assertThat(board.getField(4, 3)).isNotEqualByComparingTo(Color.W);
        assertThat(board.getField(0,7)).isEqualByComparingTo(Color.Rk);
    }

    @Test
    public void shouldNotMoveOrCapturePawnsWhenMoreThanOneIsLocatedOnKingsLineOfMove(){
        //given
        Board board = new Board();
        GameController controller = new GameController(board);
        board.generateEmptyBoard();
        board.setField(7,0, Color.Rk);
        board.setField(4,3, Color.W);
        board.setField(3,4, Color.W);
        Move move = Move.Builder.create()
                .withPawnColor(Color.Rk)
                .withStartingRow(7)
                .withStartingColumn(0)
                .withTargetRow(0)
                .withTargetColumn(7)
                .build();

        //when
        controller.move(move);

        //then
        assertThat(board.getField(7,0)).isEqualByComparingTo(Color.Rk);
        assertThat(board.getField(4, 3)).isEqualByComparingTo(Color.W);
        assertThat(board.getField(3, 4)).isEqualByComparingTo(Color.W);
        assertThat(board.getField(0,7)).isNotEqualByComparingTo(Color.Rk);
    }

    @Test
    public void shouldBecomeAKingWhenRedPawnGetsToRow0(){
        //given
        Board board = new Board();
        GameController controller = new GameController(board);
        board.generateEmptyBoard();
        board.setField(1,2, Color.R);
        Move move = Move.Builder.create()
                .withPawnColor(Color.R)
                .withStartingRow(1)
                .withStartingColumn(2)
                .withTargetRow(0)
                .withTargetColumn(1)
                .build();

        //when
        controller.move(move);

        //then
        assertThat(board.getField(0,1)).isEqualByComparingTo(Color.Rk);
    }
}