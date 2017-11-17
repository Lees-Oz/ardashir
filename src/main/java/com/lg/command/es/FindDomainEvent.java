package com.lg.command.es;

public interface FindDomainEvent {
    Class<? extends DomainEvent> findClass(String name) throws ClassNotFoundException;
}
