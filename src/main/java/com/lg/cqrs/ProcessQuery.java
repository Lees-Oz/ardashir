package com.lg.cqrs;

public interface ProcessQuery {
    QueryResult process(String name, String body) throws Exception;
}
