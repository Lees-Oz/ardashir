package com.lg;

import com.github.msemys.esjc.EventStore;
import com.github.msemys.esjc.EventStoreBuilder;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.lg.cqrs.*;
import com.lg.domain.services.RollDice;
import com.lg.domain.services.RollDiceService;
import com.lg.utils.SerializeJson;
import com.lg.utils.Json;

public class GuiceModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(SerializeJson.class).to(Json.class);
        bind(ProcessCommand.class).to(CommandProcessor.class);
        bind(ProcessQuery.class).to(QueryProcessor.class);
        bind(FindCommand.class).to(CommandFinder.class);
        bind(FindQuery.class).to(QueryFinder.class);

        bind(RollDice.class).to(RollDiceService.class);
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

