package com.lg.domain.events;

import java.util.UUID;

public class PartnerJoined implements DomainEvent {
    private String gameId;
    private UUID playerId;

    public PartnerJoined(String gameId, UUID playerId) {
        this.gameId = gameId;
        this.playerId = playerId;
    }

    public String getGameId() {
        return gameId;
    }

    public UUID getPlayerId() {
        return playerId;
    }
}
