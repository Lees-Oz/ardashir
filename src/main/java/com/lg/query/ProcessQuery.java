package com.lg.query;

public interface ProcessQuery {
    QueryResult process(String name, String body) throws Exception;
}
