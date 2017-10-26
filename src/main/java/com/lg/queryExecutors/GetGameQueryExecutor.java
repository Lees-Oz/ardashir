package com.lg.queryExecutors;

import com.lg.cqrs.IExecuteQuery;
import com.lg.cqrs.IQuery;
import com.lg.messages.queries.GetGame;
import com.lg.messages.queries.GetGameResult;

public class GetGameQueryExecutor implements IExecuteQuery<GetGame> {
    @Override
    public GetGameResult execute(IQuery query) {
        GetGame qry = (GetGame) query; // Let's see if there's a way to have GetGame type in argument already

        // Read from projections
        return new GetGameResult(qry.getGameId());
    }
}
