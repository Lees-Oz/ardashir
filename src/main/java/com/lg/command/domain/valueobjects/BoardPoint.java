package com.lg.command.domain.valueobjects;

import java.util.Objects;

public class BoardPoint {
    private int index;
    private int checkersCount;
    private PlayerColor playerColor;

    public BoardPoint() {
    }

    public BoardPoint(int index, int checkersCount, PlayerColor playerColor) {
        this.index = index;
        this.checkersCount = checkersCount;
        this.playerColor = playerColor;
    }

    public int getIndex() {
        return index;
    }

    public int getCheckersCount() {
        return checkersCount;
    }

    public PlayerColor getPlayerColor() {
        return playerColor;
    }

    public BoardPoint pickChecker() {
        int newCheckersCount = this.checkersCount - 1;
        PlayerColor newPlayerColor = newCheckersCount == 0 ? null : this.playerColor;

        return new BoardPoint(this.index, newCheckersCount, newPlayerColor);
    }

    public BoardPoint putChecker(PlayerColor playerColor) {
        return new BoardPoint(index,this.checkersCount + 1, playerColor);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoardPoint that = (BoardPoint) o;
        return getCheckersCount() == that.getCheckersCount() &&
                getPlayerColor() == that.getPlayerColor();
    }

    @Override
    public int hashCode() {

        return Objects.hash(getCheckersCount(), getPlayerColor());
    }
}
