package com.lg.command.es;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.msemys.esjc.*;
import com.lg.command.domain.entities.GameSession;
import com.lg.utils.SerializeJson;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class GameRepository implements EventSourceRepository<GameSession> {
    private EventStore eventStore;
    private FindDomainEvent domainEventFinder;
    private SerializeJson serializer;

    @Inject
    public GameRepository(EventStore eventStore, FindDomainEvent domainEventFinder, SerializeJson serializer) {
        this.eventStore = eventStore;
        this.domainEventFinder = domainEventFinder;
        this.serializer = serializer;
    }

    @Override
    public GameSession get(String id) throws Exception {
        List<DomainEvent> domainEvents = new ArrayList<>();

        int batchSize = 1000;
        long nextEventNumber = 0;
        StreamEventsSlice eventsSlice;

        do {
            eventsSlice = this.eventStore.readStreamEventsForward(id, nextEventNumber, batchSize, false).get();

            for (ResolvedEvent e : eventsSlice.events) {
                String eventName = e.originalEvent().eventType;
                Class<? extends DomainEvent> eventType = this.domainEventFinder.findClass(eventName);
                DomainEvent domainEvent = (DomainEvent) this.serializer.deserialize(e.event.data, eventType);
                domainEvents.add(domainEvent);
            }

            nextEventNumber = eventsSlice.nextEventNumber;
        } while(!eventsSlice.isEndOfStream);

        return new GameSession(domainEvents, eventsSlice.lastEventNumber);
    }

    @Override
    public void save(GameSession eventSource) throws JsonProcessingException, ExecutionException, InterruptedException {
        long streamVersion = eventSource.getUnmutatedVersion();
        List<DomainEvent> mutatingEvents = eventSource.getMutatingEvents();
        List<EventData> events = new ArrayList<>();

        for (DomainEvent x : mutatingEvents) {
            events.add(EventData.newBuilder()
                .type(x.getClass().getSimpleName())
                .data(this.serializer.serialize(x))
                .metadata((String)null)
                .build());
        }

        this.eventStore.appendToStream(eventSource.getId(), streamVersion == -1 ? ExpectedVersion.NO_STREAM : streamVersion, events).get();
    }
}
