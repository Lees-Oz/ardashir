package com.lg.command.domain.events;

import com.lg.command.domain.valueobjects.BoardPoint;
import com.lg.command.domain.valueobjects.Dice;
import com.lg.command.domain.valueobjects.PlayerColor;
import com.lg.command.es.DomainEvent;

import java.util.Date;
import java.util.UUID;

public class GameStarted implements DomainEvent {
    private String gameId;
    private Dice dice;
    private UUID whitePlayerId;
    private UUID blackPlayerId;
    private PlayerColor nextPlayerColor;
    private BoardPoint[] boardPoints;

    private int version;
    private Date happenedOn;

    public GameStarted() {
    }

    public GameStarted(String gameId, Dice dice, UUID whitePlayerId, UUID blackPlayerId, PlayerColor nextPlayerColor, BoardPoint[] boardPoints) {
        this.gameId = gameId;
        this.dice = dice;
        this.whitePlayerId = whitePlayerId;
        this.blackPlayerId = blackPlayerId;
        this.nextPlayerColor = nextPlayerColor;
        this.boardPoints = boardPoints;

        this.version = 1;
        this.happenedOn = new Date();
    }

    public String getGameId() {
        return gameId;
    }

    @Override
    public int getVersion() {
        return version;
    }

    @Override
    public Date getHappenedOn() {
        return happenedOn;
    }


    public Dice getDice() {
        return dice;
    }

    public UUID getWhitePlayerId() {
        return whitePlayerId;
    }

    public UUID getBlackPlayerId() {
        return blackPlayerId;
    }

    public PlayerColor getNextPlayerColor() {
        return nextPlayerColor;
    }

    public BoardPoint[] getBoardPoints() {
        return boardPoints;
    }
}
