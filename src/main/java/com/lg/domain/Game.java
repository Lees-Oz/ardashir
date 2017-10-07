package com.lg.domain;

import java.util.UUID;

public class Game {
    public String Id;

    public final int CHECKERS_COUNT = 15;
    public final int POINTS_COUNT = 24;



    public Game(UUID id) {
        Id = id.toString();
    }

    private class Point{
        public int position;
        public String player;
    }
}
