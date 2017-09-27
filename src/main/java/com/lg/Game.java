package com.lg;

import java.util.UUID;

public class Game {
    public String Id;

    public Game() {
        Id = UUID.randomUUID().toString();
    }
}
