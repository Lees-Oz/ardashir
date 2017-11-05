package com.lg.messages.queries;

import com.lg.cqrs.Query;

public class GetMyGame implements Query {
    private String playerId;

    public GetMyGame() {
    }

    public GetMyGame(String playerId) {
        this.playerId = playerId;
    }

    public String getPlayerId() {
        return playerId;
    }
}
