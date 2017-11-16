package com.lg.es;

import java.util.Date;

public interface DomainEvent {
    int getVersion();
    Date getHappenedOn();
}
