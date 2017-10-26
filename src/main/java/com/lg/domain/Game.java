package com.lg.domain;

import com.lg.domain.events.DomainEvent;
import com.lg.domain.events.NewGameRegistered;

import java.util.UUID;

public class Game {
    private String id;

    private int dice1;
    private int dice2;

    private Board board;

    public static Game StartNewGame(String id, UUID playerId) {
        Game newGame = new Game(id);
        newGame.apply(new NewGameRegistered(id, playerId));

        return newGame;
    }

    private Game(String id) {
        this.id = id;
    }

    private void apply(DomainEvent e) {

    }

    public String getId() {
        return id;
    }
}
