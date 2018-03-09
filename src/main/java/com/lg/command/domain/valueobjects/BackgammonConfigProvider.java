package com.lg.command.domain.valueobjects;

public class BackgammonConfigProvider implements ProvideBackgammonConfig {
    @Override
    public BackgammonConfig provide() {
        return DefaultBackgammonConfig.get();
    }
}
