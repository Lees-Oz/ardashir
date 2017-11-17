package com.lg.command.domain.valueobjects;

public class Dice {
    private final int one;
    private final int two;

    public Dice(int one, int two) {
        this.one = one;
        this.two = two;
    }

    public int getOne() {
        return this.one;
    }

    public int getTwo() {
        return two;
    }
}
