package com.lg.cqrs;

public interface ExecuteQuery<T extends Query> {
    QueryResult execute(T query);
}
