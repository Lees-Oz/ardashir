package com.lg.domain;

public class CheckerMove {
    final private int from;
    final private int to;

    public CheckerMove(int from, int to) {
        this.from = from;
        this.to = to;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }
}
