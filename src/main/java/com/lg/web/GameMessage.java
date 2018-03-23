package com.lg.web;

public class GameMessage {
    private String gameId;
    private String playerId;
    private String action;

    public GameMessage() {
    }

    public GameMessage(String gameId, String playerId, String action) {
        this.gameId = gameId;
        this.playerId = playerId;
        this.action = action;
    }

    public String getGameId() {
        return gameId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public String getAction() {
        return action;
    }
}
