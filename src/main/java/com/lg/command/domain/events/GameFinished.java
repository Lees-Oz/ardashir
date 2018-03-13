package com.lg.command.domain.events;

import com.lg.command.domain.valueobjects.PlayerColor;
import com.lg.command.es.DomainEvent;

import java.util.Date;

public class GameFinished implements DomainEvent {
    private final String id;
    private final PlayerColor winnerPlayerColor;

    public GameFinished(String id, PlayerColor winnerPlayerColor) {
        this.id = id;
        this.winnerPlayerColor = winnerPlayerColor;
    }

    public String getId() {
        return id;
    }

    public PlayerColor getWinnerPlayerColor() {
        return winnerPlayerColor;
    }

    @Override
    public int getVersion() {
        return 0;
    }

    @Override
    public Date getHappenedOn() {
        return null;
    }
}
