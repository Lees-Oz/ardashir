package com.lg.cqrs;

import com.lg.messages.queries.GetMyGame;
import com.lg.queryExecutors.GetMyGameQueryExecutor;

public class QueryFinder implements FindQuery {
    @SuppressWarnings("unchecked")
    public Class<? extends Query> findQueryClass(String name) throws ClassNotFoundException {
        final String commandClassName = String.format("%s.%s", GetMyGame.class.getPackage().getName(), name);
        return (Class<? extends Query>) Class.forName(commandClassName);
    }

    @SuppressWarnings("unchecked")
    public Class<? extends ExecuteQuery> findQueryExecutorClass(String name) throws ClassNotFoundException {
        final String executorClassName = String.format("%s.%sQueryExecutor", GetMyGameQueryExecutor.class.getPackage().getName(), name);
        return (Class<? extends ExecuteQuery>) Class.forName(executorClassName);
    }
}
