package com.lg.query.executors;

import com.lg.query.ExecuteQuery;
import com.lg.query.messages.GetMyGame;
import com.lg.query.messages.GetMyGameResult;
import com.lg.query.projections.Projection;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class GetMyGameQueryExecutor implements ExecuteQuery<GetMyGame> {
    private Projection projection;

    @Inject
    public GetMyGameQueryExecutor(Projection projection) {
        this.projection = projection;
    }

    @Override
    public GetMyGameResult execute(GetMyGame query) throws IOException, ExecutionException, InterruptedException {
        Map<String, String> projection = this.projection.getMap("currentGamePerPlayerMap");
        String gameId = projection.getOrDefault(query.getPlayerId().toString(), "");

        return new GetMyGameResult(gameId);
    }
}
