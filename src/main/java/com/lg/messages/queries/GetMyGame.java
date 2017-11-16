package com.lg.messages.queries;

import com.lg.cqrs.Query;

import java.util.UUID;

public class GetMyGame implements Query {
    private UUID playerId;

    public GetMyGame() {
    }

    public GetMyGame(UUID playerId) {
        this.playerId = playerId;
    }

    public UUID getPlayerId() {
        return playerId;
    }
}
