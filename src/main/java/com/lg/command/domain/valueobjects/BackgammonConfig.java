package com.lg.command.domain.valueobjects;

import java.util.Objects;

public class BackgammonConfig implements ProvideBackgammonConfig {
    private int checkersCount;
    private int pointsCount;
    private int whiteStartPosition;
    private int blackStartPosition;
    private int domeBorder;

    public BackgammonConfig() {
        this.checkersCount = 15;
        this.pointsCount = 24;
        this.whiteStartPosition = 0;
        this.blackStartPosition = 12;
        this.domeBorder = 6;
    }

    @Override
    public int getCheckersCount() {
        return checkersCount;
    }

    @Override
    public int getPointsCount() {
        return pointsCount;
    }

    public int getWhiteStartPosition() {
        return whiteStartPosition;
    }

    public int getBlackStartPosition() {
        return blackStartPosition;
    }

    @Override
    public int getDomeBorder() {
        return domeBorder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BackgammonConfig that = (BackgammonConfig) o;
        return getCheckersCount() == that.getCheckersCount() &&
                getPointsCount() == that.getPointsCount() &&
                getWhiteStartPosition() == that.getWhiteStartPosition() &&
                getBlackStartPosition() == that.getBlackStartPosition() &&
                getDomeBorder() == that.getDomeBorder();
    }

    @Override
    public int hashCode() {

        return Objects.hash(getCheckersCount(), getPointsCount(), getWhiteStartPosition(), getBlackStartPosition(), getDomeBorder());
    }
}
