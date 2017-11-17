package com.lg.integration;

import com.lg.cqrs.QueryResult;

public interface TestQueryResult<T extends QueryResult> {
    void test(T target);
}
