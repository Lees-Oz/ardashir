package com.lg.cqrs;

import com.lg.messages.queries.GetGame;
import com.lg.queryExecutors.GetGameQueryExecutor;

public class QueryFinder implements IFindQuery {
    @SuppressWarnings("unchecked")
    public Class<? extends IQuery> findQueryClass(String name) throws ClassNotFoundException {
        final String commandClassName = String.format("%s.%s", GetGame.class.getPackage().getName(), name);
        return (Class<? extends IQuery>) Class.forName(commandClassName);
    }

    @SuppressWarnings("unchecked")
    public Class<? extends IExecuteQuery> findQueryExecutorClass(String name) throws ClassNotFoundException {
        final String executorClassName = String.format("%s.%sQueryExecutor", GetGameQueryExecutor.class.getPackage().getName(), name);
        return (Class<? extends IExecuteQuery>) Class.forName(executorClassName);
    }
}
