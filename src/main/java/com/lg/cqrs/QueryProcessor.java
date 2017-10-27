package com.lg.cqrs;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.lg.utils.IJsonSerializer;

import java.lang.reflect.Method;

public class QueryProcessor implements IProcessQuery {
    private final String METHOD_NAME = "execute";

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
        Class<? extends IQuery> queryType = this.queryFinder.findQueryClass(name);
        IQuery query = (IQuery) this.serializer.deserialize(body, queryType);

        Class<? extends IExecuteQuery> executorType = this.queryFinder.findQueryExecutorClass(name);
        IExecuteQuery<? extends IQuery> executor = (IExecuteQuery<? extends IQuery>) this.injector.getInstance(executorType);

        Method executeMethod = executorType.getMethod(METHOD_NAME, queryType);
        return (IQueryResult) executeMethod.invoke(executor, query);
    }
}
