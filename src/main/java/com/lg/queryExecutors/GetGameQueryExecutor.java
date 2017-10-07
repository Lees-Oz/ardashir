package com.lg.queryExecutors;

import com.lg.messages.queries.GetGame;
import com.lg.messages.queries.GetGameResult;

public class GetGameQueryExecutor implements IExecuteQuery<GetGame, GetGameResult> {
    @Override
    public GetGameResult execute(GetGame query) {
        return new GetGameResult();
    }
}
