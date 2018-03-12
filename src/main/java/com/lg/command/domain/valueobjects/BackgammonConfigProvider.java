package com.lg.command.domain.valueobjects;

public class BackgammonConfigProvider implements ProvideBackgammonConfig {
    @Override
    public BackgammonConfig provide() {
        return new BackgammonConfig(15, 24, 0, 12, 6);
    }
}
