package com.lg.query.projections;

import com.github.msemys.esjc.projection.ProjectionManager;
import com.github.msemys.esjc.projection.ProjectionMode;
import com.google.gson.JsonObject;
import com.lg.utils.SerializeJson;

import javax.inject.Inject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

public class Projection implements com.lg.query.projections.ProjectionManager {

    private ProjectionManager projections;
    private SerializeJson serializer;

    @Inject
    public Projection(ProjectionManager projections, SerializeJson serializer) {
        this.projections = projections;
        this.serializer = serializer;
    }

    public void initialize() throws IOException, ExecutionException, InterruptedException {
        //

        List<Path> projectionDefinitions = new ArrayList<>();

        try (Stream<Path> paths = Files.walk(Paths.get("./src/main/java/"))) {
            paths
                .filter(Files::isRegularFile)
                .filter(x -> x.getFileName().toString().endsWith(".js") && x.toAbsolutePath().toString().contains("projections"))
                .forEach(projectionDefinitions::add);
        }

        for (Path p : projectionDefinitions) {
            String content = new String(Files.readAllBytes(p));

            List<com.github.msemys.esjc.projection.Projection> projs = projections.findAll().get();

            final String projectionName = p.getFileName().toString().replace(".js", "");

            Optional<com.github.msemys.esjc.projection.Projection> a = projs.stream()
                    .filter(x -> x.effectiveName.equals(projectionName))
                    .findFirst();

            if (a.isPresent()) {
                projections.delete(projectionName);
            }

            projections.create(projectionName, content, ProjectionMode.CONTINUOUS);
        }
    }

    public Map<String, String> getMap(String name) throws ExecutionException, InterruptedException, IOException {
        String result = projections.getResult(name).get();
        return serializer.deserialize(result);
    }

    public void initStreamProjection(String projectionName, String streamId) throws ExecutionException, InterruptedException {

        // Per projection Subscribe to event, initiating the projection to exist, it's often event of stream creation
        //      Create projection in ES
        //        // Read content from js
        //        // Do replacement of stream name
        //        // Try to read this projection from ES by name
        //        // if Found - compare js, if different then replace

        List<com.github.msemys.esjc.projection.Projection> projs = projections.findAll().get();
    }
}
