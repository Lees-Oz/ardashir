package com.lg.command.domain.valueobjects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Arrays;
import java.util.List;

public class Turn {
    private List<Move> moves;

    public Turn(List<Move> moves) {
        this.moves = moves;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public Turn() {
    }

    @JsonIgnore
    public int getTotalSteps() {
        return moves.stream().mapToInt(Move::getSteps).sum();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Turn turn = (Turn) o;
        return Arrays.equals(getMoves().toArray(), turn.getMoves().toArray());
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(getMoves().toArray());
    }
}
