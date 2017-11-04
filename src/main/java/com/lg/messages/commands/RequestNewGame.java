package com.lg.messages.commands;

import com.lg.cqrs.ICommand;

import java.util.UUID;

public class RequestNewGame implements ICommand {
    private UUID playerId;

    public RequestNewGame() {
    }

    public RequestNewGame(UUID playerId) {
        this.playerId = playerId;
    }

    public UUID getPlayerId() {
        return this.playerId;
    }

    public void setPlayerId(UUID playerId) {
        this.playerId = playerId;
    }
}
