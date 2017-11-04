package com.lg.cqrs;

import com.lg.messages.queries.GetMyGame;
import com.lg.queryExecutors.GetMyGameQueryExecutor;

public class QueryFinder implements IFindQuery {
    @SuppressWarnings("unchecked")
    public Class<? extends IQuery> findQueryClass(String name) throws ClassNotFoundException {
        final String commandClassName = String.format("%s.%s", GetMyGame.class.getPackage().getName(), name);
        return (Class<? extends IQuery>) Class.forName(commandClassName);
    }

    @SuppressWarnings("unchecked")
    public Class<? extends IExecuteQuery> findQueryExecutorClass(String name) throws ClassNotFoundException {
        final String executorClassName = String.format("%s.%sQueryExecutor", GetMyGameQueryExecutor.class.getPackage().getName(), name);
        return (Class<? extends IExecuteQuery>) Class.forName(executorClassName);
    }
}
