package com.lg.query.executors;

import com.github.msemys.esjc.projection.Projection;
import com.github.msemys.esjc.projection.ProjectionManager;
import com.github.msemys.esjc.projection.ProjectionMode;
import com.lg.query.ExecuteQuery;
import com.lg.query.messages.GetMyGame;
import com.lg.query.messages.GetMyGameResult;
import com.lg.utils.SerializeJson;

import javax.inject.Inject;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class GetMyGameQueryExecutor implements ExecuteQuery<GetMyGame> {

    private ProjectionManager projections;
    private SerializeJson serializer;

    @Inject
    public GetMyGameQueryExecutor(ProjectionManager projections, SerializeJson serializer) {
        this.projections = projections;
        this.serializer = serializer;
    }

    @Override
    public GetMyGameResult execute(GetMyGame query) throws ExecutionException, InterruptedException, IOException {
        // Read from projections
        List<Projection> projs = projections.findAll().get();

        final String currentGamePerPlayerProjection = "currentGamePerPlayer";
        Optional<Projection> a = projs.stream()
            .filter(x -> x.effectiveName.equals(currentGamePerPlayerProjection))
            .findFirst();

        if (a.isPresent()) {
            projections.delete(currentGamePerPlayerProjection);
        }

        projections.create(currentGamePerPlayerProjection, "fromAll()\n" +
            ".when({\n" +
            "    $init: function() {\n" +
            "        return {}\n" +
            "    },\n" +
            "    NewGameRegistered: function(s, e) {\n" +
            "        var body = JSON.parse(e.bodyRaw);\n" +
            "        s[body.playerId] = body.gameId;\n" +
            "    },\n" +
            "    PartnerJoined: function(s, e) {\n" +
            "        var body = JSON.parse(e.bodyRaw);\n" +
            "        s[body.playerId] = body.gameId;\n" +
            "    }\n" +
            "})", ProjectionMode.CONTINUOUS);

        projs = projections.findAll().get();

        Projection myGameProj = projs.stream()
            .filter(x -> x.effectiveName.equals(currentGamePerPlayerProjection))
            .findFirst()
            .get();

        String result = projections.getResult(currentGamePerPlayerProjection).get();

        Map<String, String> prj = serializer.deserialize(result);

        String gameId = prj.getOrDefault(query.getPlayerId().toString(), "");

        return new GetMyGameResult(gameId);
    }

    public ProjectionManager getProjections() {
        return projections;
    }
}
