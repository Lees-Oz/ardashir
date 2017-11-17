package com.lg.query;

public interface FindQuery {
    Class<? extends Query> findQueryClass(String name) throws ClassNotFoundException;
    Class<? extends ExecuteQuery> findQueryExecutorClass(String name) throws ClassNotFoundException;
}
