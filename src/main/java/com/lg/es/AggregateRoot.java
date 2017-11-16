package com.lg.es;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AggregateRoot implements EventSource {
    protected String id;

    private static final String MUTATOR_METHOD_NAME = "when";
    private List<DomainEvent> mutatingEvents;
    private long unmutatedVersion;

    private static Map<String, Method> mutatorMethodsCache = new HashMap<>();

    protected AggregateRoot(List<DomainEvent> eventStream, long streamVersion) throws Exception {
        this();

        for (DomainEvent event : eventStream) {
            this.mutateWhen(event);
        }

        this.setUnmutatedVersion(streamVersion);
    }

    protected AggregateRoot() {
        this.setMutatingEvents(new ArrayList<>());
        this.setUnmutatedVersion(-1);
    }

    protected void apply(DomainEvent event) throws Exception {
        this.getMutatingEvents().add(event);
        this.mutateWhen(event);
    }

    protected void mutateWhen(DomainEvent domainEvent) throws Exception {
        Class<? extends AggregateRoot> rootType = this.getClass();
        Class<? extends DomainEvent> eventType = domainEvent.getClass();

        String methodKey = rootType.toString() + ":" + eventType.toString();
        Method method = mutatorMethodsCache.get(methodKey);

        if (method == null) {
            method = this.cacheMutatorMethodFor(methodKey, rootType, eventType);
        }

        method.invoke(this, domainEvent);
    }

    private Method cacheMutatorMethodFor(
            String aKey,
            Class<? extends AggregateRoot> rootType,
            Class<? extends DomainEvent> eventType) throws NoSuchMethodException {

        synchronized (mutatorMethodsCache) {

            Method method = rootType.getDeclaredMethod(MUTATOR_METHOD_NAME, eventType);

            method.setAccessible(true);

            mutatorMethodsCache.put(aKey, method);

            return method;
        }
    }

    public List<DomainEvent> flush() {
        List<DomainEvent> events = this.getMutatingEvents();
        this.setMutatingEvents(new ArrayList<>());
        return events;
    }

    @Override
    public String getId() {
        return id;
    }

    public List<DomainEvent> getMutatingEvents() {
        return this.mutatingEvents;
    }

    public long getUnmutatedVersion() {
        return this.unmutatedVersion;
    }


    private void setMutatingEvents(List<DomainEvent> events) {
        this.mutatingEvents = events;
    }

    private void setUnmutatedVersion(long version) {
        this.unmutatedVersion = version;
    }
}
