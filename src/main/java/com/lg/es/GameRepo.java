package com.lg.es;

import com.lg.domain.Game;

import java.util.UUID;

public class GameRepo {
    public Game getGame(String id) {
        return new Game(UUID.fromString(id));
    }
}
