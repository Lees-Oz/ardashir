package com.lg.infrastructure;

import com.github.msemys.esjc.EventStore;
import com.github.msemys.esjc.EventStoreBuilder;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

public class GuiceModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(IProcessCommand.class).to(CommandProcessor.class);
        bind(IProcessQuery.class).to(QueryProcessor.class);
    }

    @Provides
    EventStore provideEventStore() {

        EventStore eventstore = EventStoreBuilder.newBuilder()
            .singleNodeAddress("192.168.99.100", 1113)
            .userCredentials("admin", "changeit")
            .build();
        return eventstore;
    }
}

