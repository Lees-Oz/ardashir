package com.lg.domain.events;

import java.util.UUID;

public class NewGameRegistered implements DomainEvent {
    public UUID GameId;

    public NewGameRegistered(UUID gameId) {
        GameId = gameId;
    }
}
