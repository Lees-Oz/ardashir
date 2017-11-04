package com.lg.messages.queries;

import com.lg.cqrs.IQuery;

public class GetMyGame implements IQuery {
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
