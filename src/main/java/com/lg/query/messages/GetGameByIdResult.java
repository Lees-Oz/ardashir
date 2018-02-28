package com.lg.query.messages;

import com.lg.query.QueryResult;

public class GetGameByIdResult implements QueryResult {
    private String gameJson;

    public GetGameByIdResult() {
    }

    public GetGameByIdResult(String gameJson) {
        this.gameJson = gameJson;
    }

    public String getGameJson() {
        return gameJson;
    }
}
