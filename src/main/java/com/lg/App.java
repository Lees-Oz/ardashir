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

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new GuiceModule());

        put("/query/:name", injector.getInstance(QueryController.class));
        post("/command/:name", injector.getInstance(CommandController.class));
    }
}
