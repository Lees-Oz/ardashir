package com.lg;

import com.github.msemys.esjc.EventStore;
import com.github.msemys.esjc.EventStoreBuilder;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.lg.cqrs.*;
import com.lg.utils.IJsonSerializer;
import com.lg.utils.JsonSerializer;

public class GuiceModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(IJsonSerializer.class).to(JsonSerializer.class);
        bind(IProcessCommand.class).to(CommandProcessor.class);
        bind(IProcessQuery.class).to(QueryProcessor.class);
        bind(IFindCommand.class).to(CommandFinder.class);
        bind(IFindQuery.class).to(QueryFinder.class);
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

