package com.lg.cqrs;

public interface FindQuery {
    Class<? extends Query> findQueryClass(String name) throws ClassNotFoundException;
    Class<? extends ExecuteQuery> findQueryExecutorClass(String name) throws ClassNotFoundException;
}
