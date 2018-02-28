package com.lg;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.lg.query.projections.ProjectionManager;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class Projections {

    public static void main(String[] args) throws InterruptedException, ExecutionException, IOException {

        Injector injector = Guice.createInjector(new GuiceModule());

        ProjectionManager projection = injector.getInstance(ProjectionManager.class);
        projection.initialize();


    }
}
