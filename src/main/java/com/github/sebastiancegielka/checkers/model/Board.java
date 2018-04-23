package com.github.sebastiancegielka.checkers.model;

public class Board {
    private static final int SIZE = 8;
    private Color[][] board = new Color[SIZE][SIZE];

    public void generateEmptyBoard() {
        for (int m = 0; m < SIZE ; m++) {
            for (int n = 0; n < SIZE; n++) {
                board[m][n] = Color.o;
            }
        }
    }

    public void generateNewBoard() {
        for (int m = 0; m < SIZE ; m++) {
            for (int n = 0; n < SIZE; n++) {
                board[m][n] = Color.o;
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < SIZE; j+=2) {
                if (i == 1) {
                    board[i][j] = Color.W;
                } else {
                    board[i][j + 1] = Color.W;
                }
            }
        }
        for (int k = 5; k < 8; k++) {
            for (int l = 0; l < SIZE ; l+=2) {
                if(k == 6){
                    board[k][l+1] = Color.R;
                } else {
                    board[k][l] = Color.R;
                }
            }
        }
    }

    public Color getField(int x, int y){
        return board[x][y];
    }

    public void setField(int x, int y, Color field){
        board[x][y] = field;
    }

    public Color[][] deepCopy() {
        return java.util.Arrays.stream(board).map(Color[]::clone).toArray($ -> board.clone());
    }
}
