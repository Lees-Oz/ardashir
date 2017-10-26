package com.lg.domain;


import java.util.UUID;
import java.util.stream.IntStream;

public class Board {

    private final int CHECKERS_COUNT = 15;
    private final int POINTS_COUNT = 24;


    private Point[] points = new Point[POINTS_COUNT];

    private class Point{
        final private int checkersCount;
        final private UUID player;

        private Point(int checkersCount, UUID player) {
            this.checkersCount = checkersCount;
            this.player = player;
        }

        public int getCheckersCount() {
            return checkersCount;
        }

        public UUID getPlayer() {
            return player;
        }
    }

    public Board(UUID player1, UUID player2) {
        IntStream.range(0, POINTS_COUNT).forEach(i -> points[i] = new Point(0   , null));

        points[0] = new Point(CHECKERS_COUNT, player1);

        points[POINTS_COUNT / 2 + 1] = new Point(CHECKERS_COUNT, player1);
    }
}
