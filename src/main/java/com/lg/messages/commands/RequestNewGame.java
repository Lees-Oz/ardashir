package com.lg.messages.commands;

import com.lg.cqrs.ICommand;

import java.util.UUID;

public class RequestNewGame implements ICommand {
    private UUID gameId;
    private UUID playerId;

    public RequestNewGame() {
    }

    public RequestNewGame(UUID gameId, UUID playerId) {
        this.gameId = gameId;
        this.playerId = playerId;
    }

    public UUID getGameId() {
        return gameId;
    }

    public void setGameId(UUID gameId) {
        this.gameId = gameId;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public void setPlayerId(UUID playerId) {
        this.playerId = playerId;
    }
}
