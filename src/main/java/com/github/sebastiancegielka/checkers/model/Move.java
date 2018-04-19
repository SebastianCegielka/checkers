package com.github.sebastiancegielka.checkers.model;

public class Move {
    private int rowStart;
    private int columnStart;
    private int rowEnd;
    private int columnEnd;
    private Color pawn;

    private Move(int rowStart, int columnStart, int rowEnd, int columnEnd, Color pawn) {
        this.rowStart = rowStart;
        this.columnStart = columnStart;
        this.rowEnd = rowEnd;
        this.columnEnd = columnEnd;
        this.pawn = pawn;
    }

    public int getRowStart() {
        return rowStart;
    }

    public int getColumnStart() {
        return columnStart;
    }

    public int getRowEnd() {
        return rowEnd;
    }

    public int getColumnEnd() {
        return columnEnd;
    }

    public Color getPawn() {
        return pawn;
    }

    public static class Builder{
        private int rowStart;
        private int columnStart;
        private int rowEnd;
        private int columnEnd;
        private Color pawn;

        private Builder(){}

        public static Builder create(){
            return new Builder();
        }

        public Builder withStartRow(int rowStart){
            this.rowStart = rowStart;
            return this;
        }

        public Builder withStartColumn(int columnStart){
            this.columnStart = columnStart;
            return this;
        }

        public Builder withEndRow(int rowEnd){
            this.rowEnd = rowEnd;
            return this;
        }

        public Builder withColumnEnd(int columnEnd){
            this.columnEnd = columnEnd;
            return this;
        }

        public Builder withPawnColor(Color pawn){
            this.pawn = pawn;
            return this;
        }

        public Move build(){
            return new Move(rowStart, columnStart, rowEnd, columnEnd, pawn);
        }
    }
}
