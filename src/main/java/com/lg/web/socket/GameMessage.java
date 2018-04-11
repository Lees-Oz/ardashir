package com.lg.web.socket;

public class GameMessage {
    private String gameId;
    private String playerId;
    private String event;

    public GameMessage() {
    }

    public GameMessage(String gameId, String playerId, String event) {
        this.gameId = gameId;
        this.playerId = playerId;
        this.event = event;
    }

    public String getGameId() {
        return gameId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public String getEvent() {
        return event;
    }

    @Override
    public String toString() {
        return String.format("{gameId: '%s', playerId: '%s', event: '%s'}", gameId, playerId, event);
    }
}
