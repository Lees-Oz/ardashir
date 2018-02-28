package com.lg.query.messages;

import com.lg.query.Query;

import java.util.UUID;

public class GetGameById implements Query {
    private String gameId;

    public GetGameById() {
    }

    public GetGameById(String gameId) {
        this.gameId = gameId;
    }

    public String getGameId() {
        return gameId;
    }
}
