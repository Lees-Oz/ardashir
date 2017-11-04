package com.lg.domain.events;

import java.util.Date;
import java.util.UUID;

public class NewGameRegistered implements IDomainEvent {
    private String gameId;
    private UUID playerId;

    final private int version;
    final private Date happenedOn;

    public NewGameRegistered(String gameId, UUID playerId) {
        this.gameId = gameId;
        this.playerId = playerId;

        this.version = 1;
        this.happenedOn = new Date();
    }

    public String getGameId() {
        return gameId;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    @Override
    public int version() {
        return version;
    }

    @Override
    public Date happenedOn() {
        return happenedOn;
    }
}
