package com.lg.command.domain.events;

import com.lg.command.domain.valueobjects.BoardPoint;
import com.lg.command.domain.valueobjects.Dice;
import com.lg.command.domain.valueobjects.PlayerColor;
import com.lg.command.domain.valueobjects.Turn;
import com.lg.command.es.DomainEvent;

import java.util.Date;
import java.util.UUID;

public class PlayerTurned implements DomainEvent {
    private String gameId;
    private Turn turn;
    private Dice dice;
    private UUID playerId;
    private UUID nextPlayerId;
    private BoardPoint[] boardPoints;

    private int version;
    private Date happenedOn;

    public PlayerTurned() {
    }

    public PlayerTurned(String gameId, UUID playerId, Turn turn, Dice dice, BoardPoint[] boardPoints, UUID nextPlayerId) {
        this.gameId = gameId;
        this.playerId = playerId;
        this.turn = turn;
        this.dice = dice;
        this.boardPoints = boardPoints;
        this.nextPlayerId = nextPlayerId;

        this.version = 1;
        this.happenedOn = new Date();
    }

    public String getGameId() {
        return gameId;
    }

    public Turn getTurn() {
        return turn;
    }

    public Dice getDice() {
        return dice;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    @Override
    public int getVersion() {
        return version;
    }

    @Override
    public Date getHappenedOn() {
        return happenedOn;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public BoardPoint[] getBoardPoints() {
        return boardPoints;
    }

    public UUID getNextPlayerId() {
        return nextPlayerId;
    }
}
