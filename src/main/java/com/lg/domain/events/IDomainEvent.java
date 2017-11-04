package com.lg.domain.events;

import java.util.Date;

public interface IDomainEvent {
    int version();
    Date happenedOn();
}
