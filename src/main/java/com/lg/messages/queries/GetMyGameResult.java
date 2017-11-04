package com.lg.messages.queries;

import com.lg.cqrs.IQueryResult;

public class GetMyGameResult implements IQueryResult {
    private String gameId;

    public GetMyGameResult() {
    }

    public GetMyGameResult(String gameId) {
        this.gameId = gameId;
    }

    public String getGameId() {
        return gameId;
    }
}
