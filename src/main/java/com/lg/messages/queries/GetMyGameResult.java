package com.lg.messages.queries;

import com.lg.cqrs.QueryResult;

public class GetMyGameResult implements QueryResult {
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
