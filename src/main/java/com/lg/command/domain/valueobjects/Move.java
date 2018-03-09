package com.lg.command.domain.valueobjects;

import java.util.Objects;

public class Move {
    private int from;
    private int steps;

    public Move() {
    }

    public Move(int from, int steps) {
        this.from = from;
        this.steps = steps;
    }

    public int getFrom() {
        return from;
    }

    public int getSteps() {
        return steps;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Move move = (Move) o;
        return getFrom() == move.getFrom() &&
                getSteps() == move.getSteps();
    }

    @Override
    public int hashCode() {

        return Objects.hash(getFrom(), getSteps());
    }
}
