package com.lg;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.lg.query.projections.Projection;
import com.lg.query.projections.ProjectionManager;
import com.lg.web.CommandController;
import com.lg.web.QueryController;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static spark.Spark.post;
import static spark.Spark.put;

public class App {

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        Injector injector = Guice.createInjector(new GuiceModule());

        ProjectionManager projection = injector.getInstance(ProjectionManager.class);
        projection.initialize();

        put("/query/:name", injector.getInstance(QueryController.class));
        post("/command/:name", injector.getInstance(CommandController.class));
    }
}
