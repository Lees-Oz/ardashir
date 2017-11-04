package com.lg.es;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lg.domain.events.IDomainEvent;

public abstract class AggregateRoot {

    private static final String MUTATOR_METHOD_NAME = "when";
    private static Map<String, Method> mutatorMethodsCache = new HashMap<>();
    private List<IDomainEvent> mutatingEvents;
    private int unmutatedVersion;

    protected AggregateRoot(List<IDomainEvent> eventStream, int streamVersion) throws Exception {

        this();

        for (IDomainEvent event : eventStream) {
            this.mutateWhen(event);
        }

        this.setUnmutatedVersion(streamVersion);
    }

    protected AggregateRoot() {
        super();

        this.setMutatingEvents(new ArrayList<>(2));
    }

    protected void apply(IDomainEvent event) throws Exception {
        this.getMutatingEvents().add(event);
        this.mutateWhen(event);
    }

    protected void mutateWhen(IDomainEvent IDomainEvent) throws Exception {
        Class<? extends AggregateRoot> rootType = this.getClass();
        Class<? extends IDomainEvent> eventType = IDomainEvent.getClass();

        String methodKey = rootType.toString() + ":" + eventType.toString();
        Method method = mutatorMethodsCache.get(methodKey);

        if (method == null) {
            method = this.cacheMutatorMethodFor(methodKey, rootType, eventType);
        }

        method.invoke(this, IDomainEvent);
    }

    private Method cacheMutatorMethodFor(
            String aKey,
            Class<? extends AggregateRoot> rootType,
            Class<? extends IDomainEvent> eventType) throws NoSuchMethodException {

        synchronized (mutatorMethodsCache) {

            Method method = rootType.getDeclaredMethod(MUTATOR_METHOD_NAME, eventType);

            method.setAccessible(true);

            mutatorMethodsCache.put(aKey, method);

            return method;
        }
    }

    public List<IDomainEvent> flush() {
        List<IDomainEvent> events = this.getMutatingEvents();
        this.setMutatingEvents(new ArrayList<>());
        return events;
    }

    public int mutatedVersion() {
        return this.getUnmutatedVersion() + 1;
    }

    public List<IDomainEvent> getMutatingEvents() {
        return this.mutatingEvents;
    }

    public int getUnmutatedVersion() {
        return this.unmutatedVersion;
    }


    private void setMutatingEvents(List<IDomainEvent> events) {
        this.mutatingEvents = events;
    }

    private void setUnmutatedVersion(int version) {
        this.unmutatedVersion = version;
    }
}
