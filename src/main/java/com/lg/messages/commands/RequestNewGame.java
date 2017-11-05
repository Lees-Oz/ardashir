package com.lg.messages.commands;

import com.lg.cqrs.Command;

import java.util.UUID;

public class RequestNewGame implements Command {
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
