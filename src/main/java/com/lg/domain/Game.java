package com.lg.domain;

import com.lg.domain.events.GameStarted;
import com.lg.domain.events.NewGameRegistered;
import com.lg.domain.events.PartnerJoined;
import com.lg.domain.services.IRollDice;
import com.lg.domain.valueobjects.Move;
import com.lg.domain.valueobjects.Dice;
import com.lg.es.AggregateRoot;

import java.util.UUID;

public class Game extends AggregateRoot {
    private String id;

    private Dice dice;

    private UUID player1;
    private UUID player2;

    private boolean isGameStarted = false;
    private UUID nextPlayerTurn;

    public static Game registerNewGame(String id, UUID playerId) throws Exception {
        Game newGame = new Game();
        newGame.apply(new NewGameRegistered(id, playerId));

        return newGame;
    }

    public void joinGame(UUID playerId, IRollDice rollDice) throws Exception {
        if (this.player2 == playerId) {
            return;
        }

        if (this.isGameStarted) {
            throw new IllegalStateException("Game is already started.");
        }

        if (this.player2 != null) {
            throw new IllegalStateException("Another player is already joined.");
        }

        apply(new PartnerJoined(this.id, playerId));

        Dice newDice = rollDice.roll();
        apply(new GameStarted(this.id, newDice.getOne(), newDice.getTwo()));
    }

    public void doMove(UUID playerId, Move move, IRollDice rollDice) throws Exception {
        if (!this.isGameStarted) {
            throw new IllegalStateException("Game isn't yet started.");
        }

        if (playerId != this.nextPlayerTurn) {
            throw new IllegalStateException("It's another player's turn now.");
        }

        // TODO: check if move can be done

        Dice newDice = rollDice.roll();

        // TODO: check if player can make a turn, otherwise next turn is from same player
    }

    private void when(NewGameRegistered e) {
        this.id = e.getGameId();
        this.player1 = e.getPlayerId();
    }

    private void when(PartnerJoined e) {
        this.player2 = e.getPlayerId();
    }

    private void when(GameStarted e) {
        this.isGameStarted = true;
        this.nextPlayerTurn = this.player1;
        this.dice = new Dice(e.getDice1(), e.getDice2());
    }

    public String getId() {
        return id;
    }
}
