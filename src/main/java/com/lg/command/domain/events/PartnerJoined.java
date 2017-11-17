package com.lg.command.domain.events;

import com.lg.command.es.DomainEvent;

import java.util.Date;
import java.util.UUID;

public class PartnerJoined implements DomainEvent {
    private String gameId;
    private UUID playerId;

    private int version;
    private Date happenedOn;

    public PartnerJoined() {
    }

    public PartnerJoined(String gameId, UUID playerId) {
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
