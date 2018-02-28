package com.lg.query.projections;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface ProjectionManager {
    void initialize() throws IOException, ExecutionException, InterruptedException;
    Map<String, String> getMap(String projectionName) throws ExecutionException, InterruptedException, IOException;

    void initStreamProjection(String projectionName, String streamId) throws ExecutionException, InterruptedException;
}
