package com.lg.command.es;

import com.lg.command.domain.events.NewGameSessionStarted;

public class DomainEventFinder implements FindDomainEvent {
    @Override
    public Class<? extends DomainEvent> findClass(String name) throws ClassNotFoundException {
        String eventClassName = String.format("%s.%s", NewGameSessionStarted.class.getPackage().getName(), name);
        return (Class<? extends DomainEvent>) Class.forName(eventClassName);
    }
}
