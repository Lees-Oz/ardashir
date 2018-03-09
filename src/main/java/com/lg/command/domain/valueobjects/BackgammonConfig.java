package com.lg.command.domain.valueobjects;

import java.util.Objects;

public class BackgammonConfig {
    private int checkersCount;
    private int pointsCount;
    private int whiteStartPosition;
    private int blackStartPosition;
    private int domeWidth;


    public BackgammonConfig() {
    }

    public BackgammonConfig(int checkersCount, int pointsCount, int whiteStartPosition, int blackStartPosition, int domeWidth) {
        this.checkersCount = checkersCount;
        this.pointsCount = pointsCount;
        this.whiteStartPosition = whiteStartPosition;
        this.blackStartPosition = blackStartPosition;
        this.domeWidth = domeWidth;
    }

    public int getCheckersCount() {
        return checkersCount;
    }

    public int getPointsCount() {
        return pointsCount;
    }

    public int getWhiteStartPosition() {
        return whiteStartPosition;
    }

    public int getBlackStartPosition() {
        return blackStartPosition;
    }

    public int getDomeWidth() {
        return domeWidth;
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
                getDomeWidth() == that.getDomeWidth();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCheckersCount(), getPointsCount(), getWhiteStartPosition(), getBlackStartPosition(), getDomeWidth());
    }
}
