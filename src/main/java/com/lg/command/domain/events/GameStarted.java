package com.lg.command.domain.events;

import com.lg.command.domain.valueobjects.Dice;
import com.lg.command.domain.valueobjects.ProvideBackgammonConfig;
import com.lg.command.es.DomainEvent;

import java.util.Date;
import java.util.UUID;

public class GameStarted implements DomainEvent {
    private String gameId;
    private ProvideBackgammonConfig config;
    private Dice dice;
    private UUID whitePlayerId;
    private UUID blackPlayerId;

    private int version;
    private Date happenedOn;

    public GameStarted() {
    }

    public GameStarted(String gameId, ProvideBackgammonConfig config, Dice dice, UUID whitePlayerId, UUID blackPlayerId) {
        this.gameId = gameId;
        this.config = config;
        this.dice = dice;
        this.whitePlayerId = whitePlayerId;
        this.blackPlayerId = blackPlayerId;

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

    public ProvideBackgammonConfig getConfig() {
        return config;
    }

    public UUID getWhitePlayerId() {
        return whitePlayerId;
    }

    public void setWhitePlayerId(UUID whitePlayerId) {
        this.whitePlayerId = whitePlayerId;
    }

    public UUID getBlackPlayerId() {
        return blackPlayerId;
    }

    public void setBlackPlayerId(UUID blackPlayerId) {
        this.blackPlayerId = blackPlayerId;
    }
}
