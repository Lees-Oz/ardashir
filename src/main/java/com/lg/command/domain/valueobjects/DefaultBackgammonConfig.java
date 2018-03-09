package com.lg.command.domain.valueobjects;

public class DefaultBackgammonConfig {
    public static BackgammonConfig get() {
        return new BackgammonConfig(15, 24, 0, 12, 6);
    }
}
