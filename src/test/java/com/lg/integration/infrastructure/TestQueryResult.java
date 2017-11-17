package com.lg.integration.infrastructure;

import com.lg.query.QueryResult;

public interface TestQueryResult<T extends QueryResult> {
    void test(T target);
}
