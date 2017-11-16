package com.lg.cqrs;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public interface ExecuteQuery<T extends Query> {
    QueryResult execute(T query) throws ExecutionException, InterruptedException, IOException;
}
