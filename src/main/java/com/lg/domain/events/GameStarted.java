package com.lg.domain.events;

public class GameStarted implements DomainEvent {
    private String gameId;


    public GameStarted(String gameId) {
        this.gameId = gameId;
    }

    public String getGameId() {
        return gameId;
    }
}
