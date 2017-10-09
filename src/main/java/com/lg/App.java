package com.lg;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.lg.controllers.CommandController;
import com.lg.controllers.QueryController;

import static spark.Spark.post;
import static spark.Spark.put;

public class App {

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new GuiceModule());

        put("/query/:name", injector.getInstance(QueryController.class));
        post("/command/:name", injector.getInstance(CommandController.class));
    }
}
