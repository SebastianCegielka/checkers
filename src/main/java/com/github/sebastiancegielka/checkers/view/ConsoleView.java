package com.github.sebastiancegielka.checkers.view;

import com.github.sebastiancegielka.checkers.controller.GameController;
import com.github.sebastiancegielka.checkers.model.Board;
import com.github.sebastiancegielka.checkers.model.Color;
import com.github.sebastiancegielka.checkers.model.Move;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

import java.awt.*;
import java.awt.event.InputEvent;

public class ConsoleView {
    private TextIO textIO = TextIoFactory.getTextIO();
    private GameController controller;
    private TextTerminal terminal = textIO.getTextTerminal();

    public ConsoleView(GameController controller){
        this.controller = controller;
    }

    private void printBoard() {
        Color[][] cloneBoard = controller.getBoardToPrint();
        for (int i = 0; i < cloneBoard.length; i++) {
            for (int j = 0; j < cloneBoard.length; j++) {
                terminal.print(cloneBoard[i][j] + "  ");
            }
            terminal.println();
        }
    }

    private Move getMove() {
        String pawnColor = textIO.newStringInputReader()
                .withInlinePossibleValues("R", "W", "Rk", "Wk")
                .read("Enter the pawn color:");
        Color pawn = Color.valueOf(pawnColor);
        int rowStart = textIO.newIntInputReader()
                .withMinVal(0)
                .withMaxVal(7)
                .read("Enter pawn row:");
        int columnStart = textIO.newIntInputReader()
                .withMinVal(0)
                .withMaxVal(7)
                .read("Enter pawn column:");
        int rowEnd = textIO.newIntInputReader()
                .withMinVal(0)
                .withMaxVal(7)
                .read("Enter target row:");
        int columnEnd = textIO.newIntInputReader()
                .withMinVal(0)
                .withMaxVal(7)
                .read("Enter target column:");

        return Move.Builder.create()
                .withStartRow(rowStart)
                .withStartColumn(columnStart)
                .withEndRow(rowEnd)
                .withColumnEnd(columnEnd)
                .withPawnColor(pawn)
                .build();
    }

    public void run(){
        controller.test();
        printBoard();
        while(true){
            controller.move(getMove());
            printBoard();
        }
    }
}
