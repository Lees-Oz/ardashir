package com.lg.domain.events;

import com.lg.es.DomainEvent;

import java.util.Date;
import java.util.UUID;

public class NewGameRegistered implements DomainEvent {
    private String gameId;
    private UUID playerId;

    private int version;
    private Date happenedOn;

    public NewGameRegistered() {}

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
    public int getVersion() {
        return version;
    }

    @Override
    public Date getHappenedOn() {
        return happenedOn;
    }
}
