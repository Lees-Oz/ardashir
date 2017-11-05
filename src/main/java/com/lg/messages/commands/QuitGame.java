package com.lg.messages.commands;

import com.lg.cqrs.Command;

import java.util.UUID;

public class QuitGame implements Command {
    private UUID gameId;

    public QuitGame() {
    }

    public QuitGame(UUID gameId) {
        this.gameId = gameId;
    }

    public UUID getGameId() {
        return gameId;
    }
}
