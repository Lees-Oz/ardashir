package com.lg.es;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AggregateRoot {

    private static final String MUTATOR_METHOD_NAME = "when";
    private static Map<String, Method> mutatorMethodsCache = new HashMap<>();
    private List<DomainEvent> mutatingEvents;
    private int unmutatedVersion;

    protected AggregateRoot(List<DomainEvent> eventStream, int streamVersion) throws Exception {

        this();

        for (DomainEvent event : eventStream) {
            this.mutateWhen(event);
        }

        this.setUnmutatedVersion(streamVersion);
    }

    protected AggregateRoot() {
        super();

        this.setMutatingEvents(new ArrayList<>(2));
    }

    protected void apply(DomainEvent event) throws Exception {
        this.getMutatingEvents().add(event);
        this.mutateWhen(event);
    }

    protected void mutateWhen(DomainEvent DomainEvent) throws Exception {
        Class<? extends AggregateRoot> rootType = this.getClass();
        Class<? extends DomainEvent> eventType = DomainEvent.getClass();

        String methodKey = rootType.toString() + ":" + eventType.toString();
        Method method = mutatorMethodsCache.get(methodKey);

        if (method == null) {
            method = this.cacheMutatorMethodFor(methodKey, rootType, eventType);
        }

        method.invoke(this, DomainEvent);
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

    public int mutatedVersion() {
        return this.getUnmutatedVersion() + 1;
    }

    public List<DomainEvent> getMutatingEvents() {
        return this.mutatingEvents;
    }

    public int getUnmutatedVersion() {
        return this.unmutatedVersion;
    }


    private void setMutatingEvents(List<DomainEvent> events) {
        this.mutatingEvents = events;
    }

    private void setUnmutatedVersion(int version) {
        this.unmutatedVersion = version;
    }
}
