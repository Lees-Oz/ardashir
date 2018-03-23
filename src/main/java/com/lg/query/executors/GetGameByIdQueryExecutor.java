package com.lg.query.executors;

import com.lg.query.ExecuteQuery;
import com.lg.query.messages.GetGameById;
import com.lg.query.messages.GetGameByIdResult;
import com.lg.query.projections.Projection;

import javax.inject.Inject;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class GetGameByIdQueryExecutor implements ExecuteQuery<GetGameById> {
    private Projection projection;

    @Inject
    public GetGameByIdQueryExecutor(Projection projection) {
        this.projection = projection;
    }

    @Override
    public GetGameByIdResult execute(GetGameById query) throws ExecutionException, InterruptedException, IOException {
        Object game = this.projection.getPartition(GetGameByIdResult.class, "games", query.getGameId());
        return (GetGameByIdResult) game;
    }
}
