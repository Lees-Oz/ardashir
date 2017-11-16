package com.lg.es;

public interface FindDomainEvent {
    Class<? extends DomainEvent> findClass(String name) throws ClassNotFoundException;
}
