package com.lg.cqrs;

public interface IExecuteQuery<TResult extends IQueryResult> {
    TResult execute(IQuery query);
}
