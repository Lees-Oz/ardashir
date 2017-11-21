package com.lg;

import com.github.msemys.esjc.EventStore;
import com.github.msemys.esjc.EventStoreBuilder;
import com.github.msemys.esjc.projection.ProjectionManager;
import com.github.msemys.esjc.projection.ProjectionManagerBuilder;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.lg.query.FindQuery;
import com.lg.query.ProcessQuery;
import com.lg.query.QueryFinder;
import com.lg.query.QueryProcessor;
import com.lg.command.CommandFinder;
import com.lg.command.CommandProcessor;
import com.lg.command.FindCommand;
import com.lg.command.ProcessCommand;
import com.lg.command.domain.services.RollDice;
import com.lg.command.domain.services.RollDiceService;
import com.lg.command.es.GameRepository;
import com.lg.command.es.DomainEventFinder;
import com.lg.command.es.EventSourceRepository;
import com.lg.command.es.FindDomainEvent;
import com.lg.query.projections.Projection;
import com.lg.utils.SerializeJson;
import com.lg.utils.JsonSerializer;

import java.time.Duration;

public class GuiceModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(SerializeJson.class).to(JsonSerializer.class);

        // Command
        bind(ProcessCommand.class).to(CommandProcessor.class);
        bind(FindCommand.class).to(CommandFinder.class);
        bind(EventSourceRepository.class).to(GameRepository.class);
        bind(FindDomainEvent.class).to(DomainEventFinder.class);

        // Query
        bind(ProcessQuery.class).to(QueryProcessor.class);
        bind(FindQuery.class).to(QueryFinder.class);
        bind(com.lg.query.projections.ProjectionManager.class).to(Projection.class);

        // Domain
        bind(RollDice.class).to(RollDiceService.class);
    }

    @Provides
    EventStore provideEventStore() {
        return EventStoreBuilder.newBuilder()
            .singleNodeAddress("localhost", 1113)
            .userCredentials("admin", "changeit")
            .build();
    }

    @Provides
    ProjectionManager provideProjections() {
        return ProjectionManagerBuilder.newBuilder()
            .address("127.0.0.1", 2113)
            .userCredentials("admin", "changeit")
            .operationTimeout(Duration.ofSeconds(20))
            .build();
    }
}

