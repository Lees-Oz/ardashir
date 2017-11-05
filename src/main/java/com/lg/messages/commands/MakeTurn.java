package com.lg.messages.commands;

import com.lg.cqrs.Command;

public class MakeTurn implements Command {
    private String gameId;

    public MakeTurn() {
    }

    public MakeTurn(String gameId) {
        this.gameId = gameId;
    }

    public String getGameId() {
        return gameId;
    }
}
