package com.lg.messages.queries;

import com.lg.cqrs.IQueryResult;

public class GetGameResult implements IQueryResult {
    private String gameId;

    public GetGameResult() {
    }

    public GetGameResult(String gameId) {
        this.gameId = gameId;
    }

    public String getGameId() {
        return gameId;
    }
}
