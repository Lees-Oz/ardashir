package com.lg.query.projections;

import com.evanlennick.retry4j.CallExecutor;
import com.evanlennick.retry4j.config.RetryConfig;
import com.evanlennick.retry4j.config.RetryConfigBuilder;
import com.github.msemys.esjc.projection.CreateOptions;
import com.github.msemys.esjc.projection.ProjectionException;
import com.github.msemys.esjc.projection.ProjectionManager;
import com.github.msemys.esjc.projection.ProjectionMode;
import com.lg.utils.SerializeJson;

import javax.inject.Inject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

public class Projection implements com.lg.query.projections.ProjectionManager {

    private ProjectionManager projections;
    private SerializeJson serializer;

    RetryConfig config = new RetryConfigBuilder()
            .retryOnSpecificExceptions(Exception.class)
            .withMaxNumberOfTries(10)
            .withDelayBetweenTries(200, ChronoUnit.MILLIS)
            .withExponentialBackoff()
            .build();

    @Inject
    public Projection(ProjectionManager projections, SerializeJson serializer) {
        this.projections = projections;
        this.serializer = serializer;
    }

    public void initialize() throws IOException, ExecutionException, InterruptedException {
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
                System.out.println("Trying to disable and delete projection " + projectionName);
                projections.disable(projectionName).get();
                projections.delete(projectionName).get();
            }

            new CallExecutor(config).execute(() -> {
                System.out.println("Trying create projection " + projectionName);
                projections.create(projectionName, content, CreateOptions.newBuilder()
                            .mode(ProjectionMode.CONTINUOUS)
                            .emit(true)
                            .enabled(true)
                            .checkpoints(false)
                            .trackEmittedStreams(false)
                            .build()
                    ).get();
                return null;
            });
        }

        projections.shutdown();
    }

    public Map<String, String> getMap(String name) throws ExecutionException, InterruptedException, IOException {
        return serializer.deserialize(projections.getResult(name).get());
    }

    @Override
    public Object getPartition(Class targetClass, String projection, String partition) throws ExecutionException, InterruptedException, IOException {
        String json = projections.getPartitionResult(projection, partition).get();
        return serializer.deserialize(json, targetClass);
    }
}
