package com.lg.command.domain.events;

import com.lg.command.es.DomainEvent;

import java.util.Date;
import java.util.UUID;

public class NewGameSessionStarted implements DomainEvent {
    private String gameId;
    private UUID byPlayerId;

    private int version;
    private Date happenedOn;

    public NewGameSessionStarted() {}

    public NewGameSessionStarted(String gameId, UUID byPlayerId) {
        this.gameId = gameId;
        this.byPlayerId = byPlayerId;

        this.version = 1;
        this.happenedOn = new Date();
    }

    public String getGameId() {
        return gameId;
    }

    public UUID getByPlayerId() {
        return byPlayerId;
    }

    @Override
    public int getVersion() {
        return version;
    }

    @Override
    public Date getHappenedOn() {
        return happenedOn;
    }
}
