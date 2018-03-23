package com.lg;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.lg.web.CommandController;
import com.lg.web.QueryController;
import com.lg.web.WebSocketHandler;

import static spark.Spark.post;
import static spark.Spark.put;
import static spark.Spark.webSocket;

public class App {

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new GuiceModule());

        webSocket("/ws", injector.getInstance(WebSocketHandler.class));

        put("/query/:name", injector.getInstance(QueryController.class));
        post("/command/:name", injector.getInstance(CommandController.class));
    }
}
