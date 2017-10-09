package com.lg.cqrs;

public interface IFindQuery {
    Class<? extends IQuery> findQueryClass(String name) throws ClassNotFoundException;
    Class<? extends IExecuteQuery> findQueryExecutorClass(String name) throws ClassNotFoundException;
}
