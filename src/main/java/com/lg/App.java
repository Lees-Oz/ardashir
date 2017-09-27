package com.lg;

import static spark.Spark.get;
import static spark.Spark.post;

public class App {

    public static void main(String[] args) {
        get("/game/:id", (req, res) -> {
            res.status(200);
            return new Game();
        });
        post("/game", (req, res) -> {
            Game newGame = new Game();
            res.status(201);
            return newGame.Id;
        });
    }
}
