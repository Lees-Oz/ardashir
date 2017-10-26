package com.lg.messages.commands;

import com.lg.cqrs.ICommand;

public class JoinGame implements ICommand {
    private String gameId;

    public JoinGame() {
    }

    public JoinGame(String gameId) {
        this.gameId = gameId;
    }

    public String getGameId() {
        return gameId;
    }
}
