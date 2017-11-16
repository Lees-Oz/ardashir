package com.lg.es;

import java.util.List;

public interface EventSource {
    String getId();
    long getUnmutatedVersion();
    List<DomainEvent> getMutatingEvents();
}
