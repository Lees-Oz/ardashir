package com.lg.command.domain.valueobjects;

public class Move {
    final private int from1;
    final private int steps1;

    final private int from2;
    final private int steps2;


    public Move(int from1, int steps1, int from2, int steps2) {
        if (from1 <= from2) {
            this.from1 = from1;
            this.steps1 = steps1;
            this.from2 = from2;
            this.steps2 = steps2;
        } else {
            this.from1 = from2;
            this.steps1 = steps2;
            this.from2 = from1;
            this.steps2 = steps1;
        }
    }

    public int getFrom1() {
        return from1;
    }

    public int getSteps1() {
        return steps1;
    }

    public int getFrom2() {
        return from2;
    }

    public int getSteps2() {
        return steps2;
    }
}
