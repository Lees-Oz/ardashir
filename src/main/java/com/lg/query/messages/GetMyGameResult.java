package com.lg.query.messages;

import com.lg.query.QueryResult;

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
