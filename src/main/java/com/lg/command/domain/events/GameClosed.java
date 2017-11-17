package com.lg.command.domain.events;

import com.lg.command.es.DomainEvent;

import java.util.Date;

public class GameClosed implements DomainEvent {
    private String gameId;

    private int version;
    private Date happenedOn;

    public GameClosed() {
    }

    public GameClosed(String gameId) {
        this.gameId = gameId;

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
}
