package com.lg.command.domain.events;

import com.lg.command.domain.valueobjects.BackgammonConfig;
import com.lg.command.domain.valueobjects.Dice;
import com.lg.command.domain.valueobjects.PlayerColor;
import com.lg.command.es.DomainEvent;

import java.util.Date;
import java.util.UUID;

public class GameStarted implements DomainEvent {
    private String gameId;
    private BackgammonConfig gameConfig;
    private Dice dice;
    private UUID whitePlayerId;
    private UUID blackPlayerId;
    private PlayerColor nextPlayerColor;

    private int version;
    private Date happenedOn;

    public GameStarted() {
    }

    public GameStarted(String gameId, BackgammonConfig gameConfig, Dice dice, UUID whitePlayerId, UUID blackPlayerId, PlayerColor nextPlayerColor) {
        this.gameId = gameId;
        this.gameConfig = gameConfig;
        this.dice = dice;
        this.whitePlayerId = whitePlayerId;
        this.blackPlayerId = blackPlayerId;
        this.nextPlayerColor = nextPlayerColor;

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

    public BackgammonConfig getGameConfig() {
        return gameConfig;
    }

    public PlayerColor getNextPlayerColor() {
        return nextPlayerColor;
    }
}
