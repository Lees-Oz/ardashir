package com.lg.infrastructure;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.lg.messages.queries.IQuery;
import com.lg.messages.queries.IQueryResult;
import com.lg.queryExecutors.GetGameQueryExecutor;
import com.lg.queryExecutors.IExecuteQuery;

public class QueryProcessor implements IProcessQuery {
    private Injector injector;

    @Inject
    public QueryProcessor(Injector injector) {
        this.injector = injector;
    }

    @Override
    public Object process(IQuery query) throws ClassNotFoundException {
        Class t = query.getClass();
        Class executorType = Class.forName(GetGameQueryExecutor.class.getPackage().getName() + "." + t.getSimpleName() + "QueryExecutor");

        IExecuteQuery<IQuery, IQueryResult> executor = (IExecuteQuery<IQuery, IQueryResult>) this.injector.getInstance(executorType);

        return executor.execute(query);
    }
}
