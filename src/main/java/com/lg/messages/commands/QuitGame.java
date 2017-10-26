package com.lg.messages.commands;

import com.lg.cqrs.ICommand;

import java.util.UUID;

public class QuitGame implements ICommand {
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
