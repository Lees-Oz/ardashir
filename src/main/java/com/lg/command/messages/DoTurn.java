package com.lg.command.messages;

import com.lg.command.Command;
import com.lg.command.domain.valueobjects.Turn;

import java.util.UUID;

public class DoTurn implements Command {
    private String gameId;
    private UUID playerId;
    private Turn turn;

    public DoTurn() {
    }

    public DoTurn(String gameId, UUID playerId, Turn turn) {
        this.gameId = gameId;
        this.playerId = playerId;
        this.turn = turn;
    }

    public String getGameId() {
        return gameId;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public Turn getTurn() {
        return turn;
    }
}
