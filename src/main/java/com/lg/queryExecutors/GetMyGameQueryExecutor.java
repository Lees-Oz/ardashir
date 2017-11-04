package com.lg.queryExecutors;

import com.lg.cqrs.IExecuteQuery;
import com.lg.messages.queries.GetMyGame;
import com.lg.messages.queries.GetMyGameResult;

public class GetMyGameQueryExecutor implements IExecuteQuery<GetMyGame> {
    @Override
    public GetMyGameResult execute(GetMyGame query) {
        // Read from projections
        return new GetMyGameResult(query.getPlayerId());
    }
}
