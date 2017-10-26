package com.lg.messages.queries;

import com.lg.cqrs.IQuery;

public class GetGame implements IQuery {
    private String gameId;

    public GetGame() {
    }

    public GetGame(String gameId) {
        this.gameId = gameId;
    }

    public String getGameId() {
        return gameId;
    }
}
