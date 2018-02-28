package com.lg.command.domain.valueobjects;

import java.util.Objects;

public class Move {
    private int from;
    private int steps;

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

//    public int calculateSteps(int totalPoints) {
//
//        return Math.min(Math.abs(this.steps - this.from), Math.abs(this.steps + totalPoints - this.from));
//    }

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
