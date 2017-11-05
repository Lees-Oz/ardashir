package com.lg.messages.commands;

import com.lg.cqrs.Command;

public class JoinGame implements Command {
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
