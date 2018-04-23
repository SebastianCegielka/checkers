package com.github.sebastiancegielka.checkers.model;

public class Move {
    private int startingRow;
    private int startingColumn;
    private int targetRow;
    private int targetColumn;
    private Color pawn;

    private Move(int startingRow, int startingColumn, int targetRow, int targetColumn, Color pawn) {
        this.startingRow = startingRow;
        this.startingColumn = startingColumn;
        this.targetRow = targetRow;
        this.targetColumn = targetColumn;
        this.pawn = pawn;
    }

    public int getStartingRow() {
        return startingRow;
    }

    public int getStartingColumn() {
        return startingColumn;
    }

    public int getTargetRow() {
        return targetRow;
    }

    public int getTargetColumn() {
        return targetColumn;
    }

    public Color getPawn() {
        return pawn;
    }

    public static class Builder implements NeedPawnColor, NeedStartingRow, NeedStartingColumn, NeedTargetRow, NeedTargetColumn, CanBeBuild {
        private int startingRow;
        private int startingColumn;
        private int targetRow;
        private int targetColumn;
        private Color pawn;

        private Builder() {
        }

        public static NeedPawnColor create() {
            return new Builder();
        }

        @Override
        public Builder withPawnColor(Color pawn) {
            this.pawn = pawn;
            return this;
        }

        @Override
        public Builder withStartingRow(int rowStart) {
            this.startingRow = rowStart;
            return this;
        }

        @Override
        public Builder withStartingColumn(int columnStart) {
            this.startingColumn = columnStart;
            return this;
        }

        @Override
        public Builder withTargetRow(int rowEnd) {
            this.targetRow = rowEnd;
            return this;
        }

        @Override
        public Builder withTargetColumn(int columnEnd) {
            this.targetColumn = columnEnd;
            return this;
        }

        @Override
        public Move build() {
            return new Move(startingRow, startingColumn, targetRow, targetColumn, pawn);
        }
    }

    public interface NeedPawnColor {
        NeedStartingRow withPawnColor(Color pawn);
    }

    public interface NeedStartingRow {
        NeedStartingColumn withStartingRow(int rowStart);
    }

    public interface NeedStartingColumn {
        NeedTargetRow withStartingColumn(int columnStart);
    }

    public interface NeedTargetRow {
        NeedTargetColumn withTargetRow(int rowEnd);
    }

    public interface NeedTargetColumn {
        CanBeBuild withTargetColumn(int columnEnd);
    }

    public interface CanBeBuild {
        Move build();
    }
}

