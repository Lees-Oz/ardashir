package com.lg;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.lg.query.projections.ProjectionManager;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class MigrateProjections {

    public static void main(String[] args) throws InterruptedException, ExecutionException, IOException {
        Injector injector = Guice.createInjector(new GuiceModule());

        injector.getInstance(ProjectionManager.class).initialize();
    }
}
