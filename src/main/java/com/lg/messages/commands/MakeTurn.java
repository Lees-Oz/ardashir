package com.lg.messages.commands;

import com.lg.cqrs.ICommand;

public class MakeTurn implements ICommand {
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
