package com.lg.command.es;

import java.util.Date;

public interface DomainEvent {
    int getVersion();
    Date getHappenedOn();
}
