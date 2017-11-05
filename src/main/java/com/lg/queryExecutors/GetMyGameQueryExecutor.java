package com.lg.queryExecutors;

import com.lg.cqrs.ExecuteQuery;
import com.lg.messages.queries.GetMyGame;
import com.lg.messages.queries.GetMyGameResult;

public class GetMyGameQueryExecutor implements ExecuteQuery<GetMyGame> {
    @Override
    public GetMyGameResult execute(GetMyGame query) {
        // Read from projections
        return new GetMyGameResult(query.getPlayerId());
    }
}
