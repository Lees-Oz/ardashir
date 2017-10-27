package com.lg.queryExecutors;

import com.lg.cqrs.IExecuteQuery;
import com.lg.cqrs.IQuery;
import com.lg.messages.queries.GetGame;
import com.lg.messages.queries.GetGameResult;

public class GetGameQueryExecutor implements IExecuteQuery<GetGame> {
    @Override
    public GetGameResult execute(GetGame query) {
        // Read from projections
        return new GetGameResult(query.getGameId());
    }
}
