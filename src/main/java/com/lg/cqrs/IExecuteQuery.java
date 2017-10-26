package com.lg.cqrs;

public interface IExecuteQuery<TQuery extends IQuery> {
    IQueryResult execute(IQuery query);
}
