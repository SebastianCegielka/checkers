package com.github.sebastiancegielka.checkers.controller;

import com.github.sebastiancegielka.checkers.model.Board;
import com.github.sebastiancegielka.checkers.model.Color;
import com.github.sebastiancegielka.checkers.model.Move;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameController {
    private Board gameBoard = new Board();

    public Color[][] getBoardToPrint() {
        return gameBoard.deepCopy();
    }

    public void gameInit() {
        gameBoard.generateNewBoard();
    }

    public void test(){
        gameBoard.generateTestBoard();
    }

    public void move(Move move) {
        if(move.getPawn().equals(Color.Wk) || move.getPawn().equals(Color.Rk)){
            kingsMove(move);
        } else if (isPossibleToMove(move)) {
            gameBoard.setField(move.getRowStart(), move.getColumnStart(), Color.o);
            gameBoard.setField(move.getRowEnd(), move.getColumnEnd(), move.getPawn());
            becomingAKing(move);
        } else if (isPossibleToCapture(move)) {
            gameBoard.setField(move.getRowStart(), move.getColumnStart(), Color.o);
            gameBoard.setField(move.getRowEnd(), move.getColumnEnd(), move.getPawn());
            int x = (move.getRowStart() + move.getRowEnd()) /2;
            int y = (move.getColumnStart() + move.getColumnEnd()) /2;
            gameBoard.setField(x, y, Color.o);
            becomingAKing(move);
        }
    }

    private boolean isPossibleToMove(Move move) {
        if (gameBoard.getField(move.getRowEnd(), move.getColumnEnd()).equals(Color.o) &&
                gameBoard.getField(move.getRowStart(), move.getColumnStart()).equals(move.getPawn())) {
            if (move.getPawn().equals(Color.R)) {
                return move.getRowStart() == (move.getRowEnd() + 1) &&
                        (move.getColumnStart() == (move.getColumnEnd() - 1) ||
                                move.getColumnStart() == (move.getColumnEnd() + 1));
            }
            if (move.getPawn().equals(Color.W)) {
                return move.getRowStart() == (move.getRowEnd() - 1) &&
                        (move.getColumnStart() == (move.getColumnEnd() - 1) ||
                                move.getColumnStart() == (move.getColumnEnd() + 1));
            }
        }
        return false;
    }

    private boolean isPossibleToCapture(Move move) {
        if (gameBoard.getField(move.getRowEnd(), move.getColumnEnd()).equals(Color.o) &&
                gameBoard.getField(move.getRowStart(), move.getColumnStart()).equals(move.getPawn())) {
            int x = (move.getRowStart() + move.getRowEnd()) /2;
            int y = (move.getColumnStart() + move.getColumnEnd()) /2;
            if (move.getPawn().equals(Color.R)) {
                return gameBoard.getField(x, y).equals(Color.W);
            }
            if (move.getPawn().equals(Color.W)) {
                return gameBoard.getField(x, y).equals(Color.R);
            }
        }
        return false;
    }

    private void becomingAKing(Move move){
        if(move.getPawn().equals(Color.R) && move.getRowEnd() == 0){
            gameBoard.setField(move.getRowEnd(), move.getColumnEnd(), Color.Rk);
        }
        if(move.getPawn().equals(Color.W) && move.getRowEnd() == 7){
            gameBoard.setField(move.getRowEnd(), move.getColumnEnd(), Color.Wk);
        }
    }

    private void kingsMove(Move move){
        List<Color> royalMove = new ArrayList<>();
        if(move.getPawn().equals(Color.Wk) || move.getPawn().equals(Color.Rk) &&
                gameBoard.getField(move.getRowEnd(), move.getColumnEnd()).equals(Color.o) &&
                Math.abs(move.getRowStart()-move.getRowEnd()) == Math.abs(move.getColumnStart()-move.getColumnEnd())){
            if(move.getRowEnd() < move.getRowStart()){
                int moveSize = move.getRowStart() - move.getRowEnd();
                if (move.getColumnEnd() < move.getColumnStart()){
                    for (int i = 1; i < moveSize ; i++) {
                        royalMove.add(gameBoard.getField(move.getRowStart()-i, move.getColumnStart()-i));
                    }
                } else if (move.getColumnEnd() > move.getColumnStart()){
                    for (int i = 1; i < moveSize ; i++) {
                        royalMove.add(gameBoard.getField(move.getRowStart()-i, move.getColumnStart()+i));
                    }
                }
            } else if(move.getRowEnd() > move.getRowStart()){
                int moveSize = move.getRowEnd() - move.getRowStart();
                if (move.getColumnEnd() < move.getColumnStart()){
                    for (int i = 1; i < moveSize ; i++) {
                        royalMove.add(gameBoard.getField(move.getRowStart()+i, move.getColumnStart()-i));
                    }
                } else if (move.getColumnEnd() > move.getColumnStart()){
                    for (int i = 1; i < moveSize ; i++) {
                        royalMove.add(gameBoard.getField(move.getRowStart()+i, move.getColumnStart()+i));
                    }
                }
            }
            int numberOfRed = Collections.frequency(royalMove, Color.R);
            int numberOfRedK = Collections.frequency(royalMove, Color.Rk);
            int numberOfWhite = Collections.frequency(royalMove, Color.W);
            int numberOfWhiteK = Collections.frequency(royalMove, Color.Wk);

            if(move.getPawn().equals(Color.Rk)){
                if(numberOfRed+numberOfRedK == 0 && numberOfWhite+numberOfWhiteK < 2){
                    int index = -1;
                    if(numberOfWhite == 1){
                        index = royalMove.indexOf(Color.W);
                    } else if (numberOfWhiteK == 1){
                        index = royalMove.indexOf(Color.Wk);
                    }
                    kingDestroysFilthyPeasants(move, index);
                    gameBoard.setField(move.getRowStart(), move.getColumnStart(), Color.o);
                    gameBoard.setField(move.getRowEnd(), move.getColumnEnd(), move.getPawn());
                }
            } else if(move.getPawn().equals(Color.Wk)){
                if(numberOfRed+numberOfRedK < 2 && numberOfWhite+numberOfWhiteK == 0){
                    int index = -1;
                    if(numberOfRed == 1){
                        index = royalMove.indexOf(Color.R);
                    } else if (numberOfRedK == 1){
                        index = royalMove.indexOf(Color.Rk);
                    }
                    kingDestroysFilthyPeasants(move, index);
                    gameBoard.setField(move.getRowStart(), move.getColumnStart(), Color.o);
                    gameBoard.setField(move.getRowEnd(), move.getColumnEnd(), move.getPawn());
                }
            }
        }
    }

    private void kingDestroysFilthyPeasants(Move move, int index) {
        if (move.getRowEnd() < move.getRowStart()){
            if (move.getColumnEnd() < move.getColumnStart()){
                gameBoard.setField(move.getRowStart() -index-1, move.getColumnStart() -index-1, Color.o);
            }
            else if (move.getColumnEnd() > move.getColumnStart()){
                gameBoard.setField(move.getRowStart() -index-1, move.getColumnStart() +index+1, Color.o);
            }
        } else if (move.getRowEnd() > move.getRowStart()){
            if (move.getColumnEnd() < move.getColumnStart()){
                gameBoard.setField(move.getRowStart() +index+1, move.getColumnStart() -index-1, Color.o);
            }
            else if (move.getColumnEnd() > move.getColumnStart()){
                gameBoard.setField(move.getRowStart() +index+1, move.getColumnStart() +index+1, Color.o);
            }
        }
    }
}

