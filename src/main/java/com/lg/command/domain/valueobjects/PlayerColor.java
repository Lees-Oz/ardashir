package com.lg.command.domain.valueobjects;

public enum PlayerColor {
    WHITE,
    BLACK;

    public PlayerColor next() {
        return values()[(ordinal() + 1) % 2];
    }
}
