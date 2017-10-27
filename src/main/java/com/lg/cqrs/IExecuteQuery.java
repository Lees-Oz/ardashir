package com.lg.cqrs;

public interface IExecuteQuery<T extends IQuery> {
    IQueryResult execute(T query);
}
