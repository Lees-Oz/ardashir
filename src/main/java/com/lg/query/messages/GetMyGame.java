package com.lg.query.messages;

import com.lg.query.Query;

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
