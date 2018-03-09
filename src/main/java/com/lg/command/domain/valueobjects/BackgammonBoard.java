package com.lg.command.domain.valueobjects;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.IntStream;

import static com.lg.command.domain.valueobjects.PlayerColor.WHITE;

public class BackgammonBoard {
    private BackgammonConfig config;

    private BoardPoint[] points;

    private BackgammonBoard(BackgammonConfig config, BoardPoint[] points) {
        if (config == null) {
            throw new NullPointerException("Config must me valid config.");
        }

        if (points == null) {
            throw new NullPointerException("Points should be non-empty array.");
        }

        if (points.length != config.getPointsCount()) {
            throw new IllegalArgumentException(String.format("The number of points %d doesn't match configured board size %d.", points.length, config.getPointsCount()));
        }

        this.config = config;
        this.points = points;
    }

    public BackgammonBoard(BackgammonConfig config) {
        if (config == null) {
            throw new NullPointerException("Config must me valid config.");
        }

        this.config = config;

        points = new BoardPoint[config.getPointsCount()];

        // Set checkers to initial position
        IntStream.range(0, config.getPointsCount()).forEach(i ->
                points[i] = new BoardPoint(i,0   , null)
        );

        points[config.getWhiteStartPosition()] = new BoardPoint(getStartPosition(PlayerColor.WHITE), config.getCheckersCount(), PlayerColor.WHITE);
        points[config.getBlackStartPosition()] = new BoardPoint(getStartPosition(PlayerColor.BLACK), config.getCheckersCount(), PlayerColor.BLACK);
    }

    public BoardPoint[] getPoints() {
        return points;
    }

    public BackgammonBoard asIfMoved(PlayerColor playerColor, Move move) {
        if (!canMove(playerColor, move)) {
            return null;
        }

        int destinationIndex = (move.getFrom() + move.getSteps()) % config.getPointsCount();

        BoardPoint[] newPoints = points.clone();

        newPoints[move.getFrom()] = newPoints[move.getFrom()].pickChecker();
        newPoints[destinationIndex] = newPoints[destinationIndex].putChecker(playerColor);

        return new BackgammonBoard(config, newPoints);
    }

    public boolean canMove(PlayerColor playerColor, Move move) {
        BoardPoint fromPoint = points[move.getFrom()];
        if (fromPoint.getPlayerColor() != playerColor) {
            return false;
        }

        int checkerTotalStepsDone = (move.getFrom() - getStartPosition(playerColor) + config.getPointsCount()) % config.getPointsCount();

        // If checker finishes its round and gets removed, all other player checkers need to be in dome
        if (checkerTotalStepsDone + move.getSteps() >= config.getPointsCount()) {

            return Arrays.stream(points)
                    .filter(x -> x.getPlayerColor() == playerColor)
                    .allMatch(x -> x.getIndex() >= config.getPointsCount() - config.getDomeWidth());
        }

        int destinationIndex = (move.getFrom() + move.getSteps()) % config.getPointsCount();

        BoardPoint toPoint = points[destinationIndex];
        return toPoint.getPlayerColor() == playerColor || toPoint.getPlayerColor() == null;
    }

    public BackgammonBoard asIfTurned(PlayerColor playerColor, Turn turn) {
        BackgammonBoard result = this;

        for (Move m : turn.getMoves()) {
            result = result.asIfMoved(playerColor, m);
        }

        return result;
    }

    private int getStartPosition(PlayerColor playerColor) {
        if (playerColor == WHITE){
            return config.getWhiteStartPosition();
        }

        return config.getBlackStartPosition();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BackgammonBoard that = (BackgammonBoard) o;
        return Objects.equals(config, that.config) &&
                Arrays.equals(points, that.points);
    }

    @Override
    public int hashCode() {

        int result = Objects.hash(config);
        result = 31 * result + Arrays.hashCode(points);
        return result;
    }
}
