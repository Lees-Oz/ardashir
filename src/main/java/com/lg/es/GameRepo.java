package com.lg.es;

import com.lg.domain.Game;

import java.util.UUID;

import static com.lg.domain.Game.registerNewGame;

public class GameRepo {
    public Game getGame(String id) throws Exception {
        // TODO: retrieve from event store
        return registerNewGame(UUID.randomUUID().toString(), UUID.randomUUID());
    }

    public void save(Game game){
        // TODO: save to event store
    }
}
