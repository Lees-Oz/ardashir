package com.lg.domain.events;

import com.lg.domain.CheckerMove;

import java.util.ArrayList;
import java.util.UUID;

public class PlayerTurned implements DomainEvent {
    final private String gameId;
    final private UUID playerId;
    final private ArrayList<CheckerMove> moves;
    final private int dice1;
    final private int dice2;

    public PlayerTurned(String gameId, UUID playerId, ArrayList<CheckerMove> moves, int dice1, int dice2) {
        this.gameId = gameId;
        this.playerId = playerId;
        this.moves = moves;
        this.dice1 = dice1;
        this.dice2 = dice2;
    }

    public String getGameId() {
        return gameId;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public ArrayList<CheckerMove> getMoves() {
        return moves;
    }

    public int getDice1() {
        return dice1;
    }

    public int getDice2() {
        return dice2;
    }
}
