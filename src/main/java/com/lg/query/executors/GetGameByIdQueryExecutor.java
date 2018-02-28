package com.lg.query.executors;

import com.lg.query.ExecuteQuery;
import com.lg.query.messages.GetGameById;
import com.lg.query.messages.GetGameByIdResult;
import com.lg.query.projections.Projection;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class GetGameByIdQueryExecutor implements ExecuteQuery<GetGameById> {
    private Projection projection;

    @Inject
    public GetGameByIdQueryExecutor(Projection projection) {
        this.projection = projection;
    }

    @Override
    public GetGameByIdResult execute(GetGameById query) throws IOException, ExecutionException, InterruptedException {
        Map<String, String> projection = this.projection.getMap("Games");
        String game = projection.getOrDefault(query.getGameId(), null);

        return new GetGameByIdResult(game);
    }
}
