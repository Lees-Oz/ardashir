package com.lg.queryExecutors;

import com.lg.cqrs.IExecuteQuery;
import com.lg.cqrs.IQuery;
import com.lg.messages.queries.GetGameResult;

public class GetGameQueryExecutor implements IExecuteQuery<GetGameResult> {
    @Override
    public GetGameResult execute(IQuery query) {
        return new GetGameResult();
    }
}
