package com.lg.messages.commands;

import com.lg.cqrs.Command;

import java.util.UUID;

public class JoinGame implements Command {
    private String gameId;
    private UUID playerId;

    public JoinGame() {
    }

    public JoinGame(String gameId, UUID playerId) {
        this.gameId = gameId;
        this.playerId = playerId;
    }

    public String getGameId() {
        return gameId;
    }

    public UUID getPlayerId() {
        return playerId;
    }
}
