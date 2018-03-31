package com.lg.command.domain.valueobjects;

import com.google.common.collect.ImmutableList;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.lg.command.domain.valueobjects.PlayerColor.BLACK;
import static com.lg.command.domain.valueobjects.PlayerColor.WHITE;

public class BackgammonBoard {
    private BackgammonConfig config;

    private BoardPoint[] points;

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

        points[config.getWhiteStartPosition()] = new BoardPoint(config.getWhiteStartPosition(), config.getCheckersCount(), PlayerColor.WHITE);
        points[config.getBlackStartPosition()] = new BoardPoint(config.getBlackStartPosition(), config.getCheckersCount(), PlayerColor.BLACK);
    }

    public BackgammonBoard(BackgammonConfig config, BoardPoint[] points) {
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

    public BackgammonBoard tryTurn(PlayerColor playerColor, Turn turn, Steps steps) {
        BackgammonBoard resultBoard = this.asIfTurned(playerColor, turn);
        List<BackgammonBoard> possibleBoards = this.getPossibleBoardsForSteps(playerColor, steps);

        if (possibleBoards.contains(resultBoard)) {
            return resultBoard;
        }

        return null;
    }

    public BoardPoint[] getPoints() {
        return points;
    }

    public BackgammonBoard asIfTurned(PlayerColor playerColor, Turn turn) {
        BackgammonBoard result = this;

        for (Move m : turn.getMoves()) {
            BackgammonBoard nextBoard = result.asIfMoved(playerColor, m);
            if (nextBoard == null) {
                return null;
            }
            result = nextBoard;
        }

        return result;
    } // Board + Turn = ? Board

    private BackgammonBoard asIfMoved(PlayerColor playerColor, Move move) {
        if (!canMove(playerColor, move)) {
            return null;
        }

        int destinationIndex = (move.getFrom() + move.getSteps()) % config.getPointsCount();
        BoardPoint[] newPoints = points.clone();

        newPoints[move.getFrom()] = newPoints[move.getFrom()].pickChecker();

        int playerStartPosition = playerColor == WHITE ? config.getWhiteStartPosition() : config.getBlackStartPosition();
        int checkerTotalStepsDone = (move.getFrom() - playerStartPosition + config.getPointsCount()) % config.getPointsCount();

        // If checker finishes its round and gets removed, all other player checkers need to be in dome
        if (checkerTotalStepsDone + move.getSteps() < config.getPointsCount()) {
            newPoints[destinationIndex] = newPoints[destinationIndex].putChecker(playerColor);
        }

        return new BackgammonBoard(config, newPoints);
    }



    private List<BackgammonBoard> getPossibleBoardsForSteps(PlayerColor playerColor, Steps steps) {
        List<TurnCandidate> candidates = new ArrayList<>();
        candidates.add(new TurnCandidate(new Turn(ImmutableList.of()), this));
        TurnCandidate emptyTurnCandidate = new TurnCandidate(new Turn(ImmutableList.of()), this);

        for (int[] combination : new int[][] {
                new int[] {steps.get(0), steps.get(1)},
                new int[] {steps.get(1), steps.get(0)}
        }) {
            for (int i = 0; i < config.getPointsCount(); i++) {
                Move move1 = new Move(i, combination[0]);
                BackgammonBoard nextBoard1 = this.asIfMoved(playerColor, move1);

                if (nextBoard1 != null) {
                    candidates.add(new TurnCandidate(new Turn(ImmutableList.of(move1)), nextBoard1));

                    for (int j = 0; j < config.getPointsCount(); j++) {
                        Move move2 = new Move(j, combination[1]);

                        BackgammonBoard nextBoard2 = nextBoard1.asIfMoved(playerColor, move2);
                        if (nextBoard2 != null) {
                            candidates.add(new TurnCandidate(new Turn(ImmutableList.of(move1, move2)), nextBoard2));
                        }
                    }
                }
            }
        }
//        } else if(steps.length == 4) {
//            // calculate here for double dice
//        }

        TurnCandidate longestCandidate = candidates.stream()
                .max(Comparator.comparingInt(o -> o.getTurn().getTotalSteps()))
                .orElse(emptyTurnCandidate);

        int longest = longestCandidate.getTurn().getTotalSteps();

        return candidates.stream()
                .filter(x -> x.getTurn().getTotalSteps() == longest)
                .map(TurnCandidate::getBoard)
                .distinct()
                .collect(Collectors.toList());
    } // f(board, steps) = Turn[] or Board[]

    private boolean canMove(PlayerColor playerColor, Move move) {
        BoardPoint fromPoint = points[move.getFrom()];
        if (fromPoint.getPlayerColor() != playerColor) {
            return false;
        }

        int playerStartPosition = playerColor == WHITE ? config.getWhiteStartPosition() : config.getBlackStartPosition();
        int checkerTotalStepsDone = (move.getFrom() - playerStartPosition + config.getPointsCount()) % config.getPointsCount();

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

    public BackgammonConfig getConfig() {
        return config;
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

    public PlayerColor getWinner() {
        if (Arrays.stream(points).allMatch(x -> x.getPlayerColor() != WHITE)) {
            return WHITE;
        } else if (Arrays.stream(points).allMatch(x -> x.getPlayerColor() != BLACK)) {
            return BLACK;
        }

        return null;
    }
}
