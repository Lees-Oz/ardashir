package com.lg.es;

import com.lg.domain.Game;

import java.util.UUID;

public class GameRepo {
    public Game getGame(String id) {
        // Raise from event store
        return Game.StartNewGame(UUID.randomUUID().toString(), UUID.randomUUID());
    }

    public void save(Game game){

    }
}
