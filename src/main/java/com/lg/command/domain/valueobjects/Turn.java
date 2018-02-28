package com.lg.command.domain.valueobjects;

import java.util.Arrays;

public class Turn {
    final private Move[] moves;

    public Turn(Move[] moves) {
        this.moves = moves;
    }

    public Move[] getMoves() {
        return moves;
    }

    public int getTotalSteps() {
        return Arrays.stream(moves).mapToInt(Move::getSteps).sum();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Turn turn = (Turn) o;
        return Arrays.equals(getMoves(), turn.getMoves());
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(getMoves());
    }
}
