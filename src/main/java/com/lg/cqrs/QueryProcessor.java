package com.lg.cqrs;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.lg.utils.IJsonSerializer;

public class QueryProcessor implements IProcessQuery {
    private IFindQuery queryFinder;
    private IJsonSerializer serializer;
    private Injector injector;

    @Inject
    public QueryProcessor(IFindQuery queryFinder, IJsonSerializer serializer, Injector injector) {
        this.queryFinder = queryFinder;
        this.serializer = serializer;
        this.injector = injector;
    }

    @Override
    public IQueryResult process(String name, String body) throws Exception {
        Class<? extends IQuery> queryClass = this.queryFinder.findQueryClass(name);
        IQuery query = (IQuery) this.serializer.deserialize(body, queryClass);
        Class executorType = this.queryFinder.findQueryExecutorClass(name);

        IExecuteQuery<?> executor = (IExecuteQuery<?>) this.injector.getInstance(executorType);

        return executor.execute(query);
    }
}
