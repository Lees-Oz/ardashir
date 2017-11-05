package com.lg.cqrs;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.lg.utils.SerializeJson;

import java.lang.reflect.Method;

public class QueryProcessor implements ProcessQuery {
    private final String METHOD_NAME = "execute";

    private FindQuery queryFinder;
    private SerializeJson serializer;
    private Injector injector;

    @Inject
    public QueryProcessor(FindQuery queryFinder, SerializeJson serializer, Injector injector) {
        this.queryFinder = queryFinder;
        this.serializer = serializer;
        this.injector = injector;
    }

    @Override
    public QueryResult process(String name, String body) throws Exception {
        Class<? extends Query> queryType = this.queryFinder.findQueryClass(name);
        Query query = (Query) this.serializer.deserialize(body, queryType);

        Class<? extends ExecuteQuery> executorType = this.queryFinder.findQueryExecutorClass(name);
        ExecuteQuery<? extends Query> executor = (ExecuteQuery<? extends Query>) this.injector.getInstance(executorType);

        Method executeMethod = executorType.getMethod(METHOD_NAME, queryType);
        return (QueryResult) executeMethod.invoke(executor, query);
    }
}
