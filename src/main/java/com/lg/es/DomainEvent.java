package com.lg.es;

import java.util.Date;

public interface DomainEvent {
    int version();
    Date happenedOn();
}
