package com.lg.command.es;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.concurrent.ExecutionException;

public interface EventSourceRepository<T extends EventSource> {
    T get(String id) throws Exception;
    void save(T eventSource) throws JsonProcessingException, ExecutionException, InterruptedException;
}
