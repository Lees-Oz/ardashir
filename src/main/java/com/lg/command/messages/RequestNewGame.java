package com.lg.command.messages;

import com.lg.command.Command;

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
}
