package com.lg.domain.events;

import com.lg.es.DomainEvent;

import java.util.Date;

public class GameStarted implements DomainEvent {
    private String gameId;
    private int dice1;
    private int dice2;

    private int version;
    private Date happenedOn;

    public GameStarted() {
    }

    public GameStarted(String gameId, int dice1, int dice2) {
        this.gameId = gameId;
        this.dice1 = dice1;
        this.dice2 = dice2;


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

    public int getDice1() {
        return dice1;
    }

    public int getDice2() {
        return dice2;
    }
}
