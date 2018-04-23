package com.github.sebastiancegielka.checkers.controller;

import com.github.sebastiancegielka.checkers.model.Board;
import com.github.sebastiancegielka.checkers.model.Color;
import com.github.sebastiancegielka.checkers.model.Move;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameController {
    private final Color EMPTY = Color.o;
    private Board gameBoard = new Board();
    private int whitePawnsRemaining = 12;
    private int redPawnsRemaining = 12;

    public GameController() {
    }

    GameController(Board gameBoard) {
        this.gameBoard = gameBoard;
    }

    public Color[][] getBoardToPrint() {
        return gameBoard.deepCopy();
    }

    public void gameInit() {
        gameBoard.generateNewBoard();
    }

    private void removePawns(Move move) {
        if (move.getPawn() == Color.R || move.getPawn() == Color.Rk) {
            whitePawnsRemaining--;
        } else if (move.getPawn() == Color.Wk || move.getPawn() == Color.Wk) {
            redPawnsRemaining--;
        }
    }

    public void move(Move move) {

        if (move.getPawn().equals(Color.Wk) || move.getPawn().equals(Color.Rk)) {
            kingsMove(move);
        } else if (isPossibleToMove(move)) {
            gameBoard.setField(move.getStartingRow(), move.getStartingColumn(), EMPTY);
            gameBoard.setField(move.getTargetRow(), move.getTargetColumn(), move.getPawn());
            becomingAKing(move);
        } else if (isPossibleToCapture(move)) {
            gameBoard.setField(move.getStartingRow(), move.getStartingColumn(), EMPTY);
            gameBoard.setField(move.getTargetRow(), move.getTargetColumn(), move.getPawn());
            int x = (move.getStartingRow() + move.getTargetRow()) / 2;
            int y = (move.getStartingColumn() + move.getTargetColumn()) / 2;
            gameBoard.setField(x, y, EMPTY);
            removePawns(move);
            becomingAKing(move);
        }
    }

    private boolean isPossibleToMove(Move move) {
        if (isThereAPawnInStartingField_isTargetFieldEmpty(move)) {
            if (move.getPawn().equals(Color.R)) {
                return isMoveDiagonalUpLeftOrRight(move);
            }
            if (move.getPawn().equals(Color.W)) {
                return isMoveDiagonalDownLeftOrRight(move);
            }
        }
        return false;
    }

    private boolean isMoveDiagonalDownLeftOrRight(Move move) {
        return move.getStartingRow() == (move.getTargetRow() - 1) &&
                ((move.getStartingColumn() == move.getTargetColumn() - 1) ||
                        move.getStartingColumn() == (move.getTargetColumn() + 1));
    }

    private boolean isMoveDiagonalUpLeftOrRight(Move move) {
        return move.getStartingRow() == (move.getTargetRow() + 1) &&
                (move.getStartingColumn() == (move.getTargetColumn() - 1) ||
                        move.getStartingColumn() == (move.getTargetColumn() + 1));
    }

    private boolean isPossibleToCapture(Move move) {
        if (isThereAPawnInStartingField_isTargetFieldEmpty(move)) {
            int x = (move.getStartingRow() + move.getTargetRow()) / 2;
            int y = (move.getStartingColumn() + move.getTargetColumn()) / 2;
            if (move.getPawn().equals(Color.R)) {
                return gameBoard.getField(x, y).equals(Color.W);
            }
            if (move.getPawn().equals(Color.W)) {
                return gameBoard.getField(x, y).equals(Color.R);
            }
        }
        return false;
    }

    private boolean isThereAPawnInStartingField_isTargetFieldEmpty(Move move) {
        return gameBoard.getField(move.getTargetRow(), move.getTargetColumn()).equals(EMPTY) &&
                gameBoard.getField(move.getStartingRow(), move.getStartingColumn()).equals(move.getPawn());
    }

    private void becomingAKing(Move move) {
        if (move.getPawn().equals(Color.R) && move.getTargetRow() == 0) {
            gameBoard.setField(move.getTargetRow(), move.getTargetColumn(), Color.Rk);
        }
        if (move.getPawn().equals(Color.W) && move.getTargetRow() == 7) {
            gameBoard.setField(move.getTargetRow(), move.getTargetColumn(), Color.Wk);
        }
    }

    private void kingsMove(Move move) {
        List<Color> royalMove = new ArrayList<>();
        boolean moveUp = move.getTargetRow() < move.getStartingRow();
        boolean moveLeft = move.getTargetColumn() < move.getStartingColumn();
        boolean moveRight = move.getTargetColumn() > move.getStartingColumn();
        boolean moveDown = move.getTargetRow() > move.getStartingRow();

        if (isMoveDiagonal_isTargetFieldEmpty(move)) {
            if (moveUp) {
                int moveSize = move.getStartingRow() - move.getTargetRow();
                if (moveLeft) {
                    for (int i = 1; i < moveSize; i++) {
                        royalMove.add(gameBoard.getField(move.getStartingRow() - i, move.getStartingColumn() - i));
                    }
                } else if (moveRight) {
                    for (int i = 1; i < moveSize; i++) {
                        royalMove.add(gameBoard.getField(move.getStartingRow() - i, move.getStartingColumn() + i));
                    }
                }
            } else {
                if (moveDown) {
                    int moveSize = move.getTargetRow() - move.getStartingRow();
                    if (moveLeft) {
                        for (int i = 1; i < moveSize; i++) {
                            royalMove.add(gameBoard.getField(move.getStartingRow() + i, move.getStartingColumn() - i));
                        }
                    } else if (moveRight) {
                        for (int i = 1; i < moveSize; i++) {
                            royalMove.add(gameBoard.getField(move.getStartingRow() + i, move.getStartingColumn() + i));
                        }
                    }
                }
            }
            kingsPath(move, royalMove);
        }
    }

    private void kingsPath(Move move, List<Color> royalMove) {
        int numberOfRed = Collections.frequency(royalMove, Color.R);
        int numberOfRedK = Collections.frequency(royalMove, Color.Rk);
        int numberOfWhite = Collections.frequency(royalMove, Color.W);
        int numberOfWhiteK = Collections.frequency(royalMove, Color.Wk);
        int overallRedPawns = numberOfRed + numberOfRedK;
        int overallWhitePawns = numberOfWhite + numberOfWhiteK;

        if (move.getPawn().equals(Color.Rk)) {
            if (overallRedPawns == 0 && overallWhitePawns < 2) {
                int index = -1;
                if (numberOfWhite == 1) {
                    index = royalMove.indexOf(Color.W);
                } else if (numberOfWhiteK == 1) {
                    index = royalMove.indexOf(Color.Wk);
                }
                kingDestroysFilthyPeasants(move, index);
                gameBoard.setField(move.getStartingRow(), move.getStartingColumn(), EMPTY);
                gameBoard.setField(move.getTargetRow(), move.getTargetColumn(), move.getPawn());
            }
        } else if (move.getPawn().equals(Color.Wk)) {
            if (overallRedPawns < 2 && overallWhitePawns == 0) {
                int index = -1;
                if (numberOfRed == 1) {
                    index = royalMove.indexOf(Color.R);
                } else if (numberOfRedK == 1) {
                    index = royalMove.indexOf(Color.Rk);
                }
                kingDestroysFilthyPeasants(move, index);
                gameBoard.setField(move.getStartingRow(), move.getStartingColumn(), EMPTY);
                gameBoard.setField(move.getTargetRow(), move.getTargetColumn(), move.getPawn());
            }
        }
    }

    private boolean isMoveDiagonal_isTargetFieldEmpty(Move move) {
        return gameBoard.getField(move.getTargetRow(), move.getTargetColumn()).equals(EMPTY) &&
                Math.abs(move.getStartingRow() - move.getTargetRow()) == Math.abs(move.getStartingColumn() - move.getTargetColumn());
    }

    private void kingDestroysFilthyPeasants(Move move, int index) {
        boolean moveUp = move.getTargetRow() < move.getStartingRow();
        boolean moveLeft = move.getTargetColumn() < move.getStartingColumn();
        boolean moveRight = move.getTargetColumn() > move.getStartingColumn();
        boolean moveDown = move.getTargetRow() > move.getStartingRow();

        if (moveUp) {
            if (moveLeft) {
                gameBoard.setField(move.getStartingRow() - index - 1, move.getStartingColumn() - index - 1, EMPTY);
                removePawns(move);
            } else if (moveRight) {
                gameBoard.setField(move.getStartingRow() - index - 1, move.getStartingColumn() + index + 1, EMPTY);
                removePawns(move);
            }
        } else if (moveDown) {
            if (moveLeft) {
                gameBoard.setField(move.getStartingRow() + index + 1, move.getStartingColumn() - index - 1, EMPTY);
                removePawns(move);
            } else if (moveRight) {
                gameBoard.setField(move.getStartingRow() + index + 1, move.getStartingColumn() + index + 1, EMPTY);
                removePawns(move);
            }
        }
    }

    public boolean allOpponentsPawnsCaptured(){
        return whitePawnsRemaining != 0 && redPawnsRemaining != 0;
    }

    public int getWhitePawnsRemaining() {
        return whitePawnsRemaining;
    }

    public int getRedPawnsRemaining() {
        return redPawnsRemaining;
    }
}

