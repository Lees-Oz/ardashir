package com.lg.queryExecutors;

import com.lg.messages.queries.IQuery;
import com.lg.messages.queries.IQueryResult;

public interface IExecuteQuery<TQuery extends IQuery, TResult extends IQueryResult> {
    TResult execute(TQuery query);
}
