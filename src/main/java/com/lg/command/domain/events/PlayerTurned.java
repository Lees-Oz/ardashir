package com.lg.command.domain.events;

import com.lg.command.domain.valueobjects.*;
import com.lg.command.es.DomainEvent;

import java.util.Date;

public class PlayerTurned implements DomainEvent {
    private String gameId;
    private Turn turn;
    private Dice nextDice; // <---- maybe have it as separate event like PlayerRolledDice
    private PlayerColor playerColor;
    private BoardPoint[] boardPoints;

    private int version;
    private Date happenedOn;

    public PlayerTurned() {
    }

    public PlayerTurned(PlayerColor playerColor, Turn turn, Dice nextDice, BoardPoint[] boardPoints) {
        this.playerColor = playerColor;
        this.turn = turn;
        this.nextDice = nextDice;
        this.boardPoints = boardPoints;

        this.version = 1;
        this.happenedOn = new Date();
    }

    public String getGameId() {
        return gameId;
    }

    public Turn getTurn() {
        return turn;
    }

    public Dice getNextDice() {
        return nextDice;
    }

    public PlayerColor getPlayerColor() {
        return playerColor;
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
}
