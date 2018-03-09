package com.lg.command.domain.valueobjects;

import java.util.Arrays;
import java.util.Objects;

public class Dice {
    private int one;
    private int two;

    public Dice() {
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dice dice = (Dice) o;
        return (getOne() == dice.getOne() && getTwo() == dice.getTwo()) ||
                (getOne() == dice.getTwo() && getTwo() == dice.getOne());
    }

    @Override
    public int hashCode() {
        int[] array = new int[]{getOne(), getTwo()};
        Arrays.sort(array);
        return Objects.hash(array[0], array[1]);
    }
}
