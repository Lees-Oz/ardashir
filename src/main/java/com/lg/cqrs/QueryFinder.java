package com.lg.cqrs;

import com.lg.messages.queries.GetGame;
import com.lg.queryExecutors.GetGameQueryExecutor;

public class QueryFinder implements IFindQuery {
    public Class<? extends IQuery> findQueryClass(String name) throws ClassNotFoundException {
        final String commandClassName = String.format("%s.%s", GetGame.class.getPackage().getName(), name);
        return (Class<? extends IQuery>) Class.forName(commandClassName);
    }

    public Class<? extends IExecuteQuery> findQueryExecutorClass(String name) throws ClassNotFoundException {
        final String executorClassName = String.format("%s.%sCommandExecutor", GetGameQueryExecutor.class.getPackage().getName(), name);
        return (Class<? extends IExecuteQuery>) Class.forName(executorClassName);
    }
}
