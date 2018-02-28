package com.lg.command.domain.entities;

import com.lg.command.domain.events.GameStarted;
import com.lg.command.domain.events.NewGameSessionStarted;
import com.lg.command.domain.events.PartnerJoinedGameSession;
import com.lg.command.domain.events.PlayerTurned;
import com.lg.command.domain.services.RollDice;
import com.lg.command.domain.valueobjects.BackgammonGame;
import com.lg.command.domain.valueobjects.PlayerColor;
import com.lg.command.domain.valueobjects.ProvideBackgammonConfig;
import com.lg.command.domain.valueobjects.Turn;
import com.lg.command.es.AggregateRoot;
import com.lg.command.es.DomainEvent;
import com.sun.javaws.exceptions.InvalidArgumentException;

import java.util.List;
import java.util.UUID;

public class GameSession extends AggregateRoot {
    private UUID playerWhite;
    private UUID playerBlack;

    private boolean isGameInProgress = false; // Status

    private BackgammonGame game = null;

    public static GameSession startNewGameSession(String id, UUID playerId) throws Exception {
        GameSession newGameSession = new GameSession();
        newGameSession.apply(new NewGameSessionStarted(id, playerId));

        return newGameSession;
    }

    private GameSession() {
    }

    public GameSession(List<DomainEvent> eventStream, long version) throws Exception {
        super(eventStream, version);
    }

    public void joinGameSession(UUID playerId, RollDice rollDice, ProvideBackgammonConfig gameConfig) throws Exception {
        if (this.playerBlack == playerId) {
            return;
        }

        if (this.isGameInProgress) {
            throw new IllegalStateException("GameSession is already started.");
        }

        if (this.playerBlack != null) {
            throw new IllegalStateException("Another player is already joined.");
        }

        apply(new PartnerJoinedGameSession(this.id, playerId));
        apply(new GameStarted(this.id, gameConfig, rollDice.roll(), playerWhite, playerBlack));
    }

    public void doTurn(UUID playerId, Turn turn, RollDice rollDice) throws Exception {
        PlayerColor playerColor = this.getPlayerColorById(playerId);
        if (playerColor == null) {
            throw new InvalidArgumentException(new String[]{"Such player doesn't exist."});
        }

        if (!this.isGameInProgress) {
            throw new IllegalStateException("GameSession isn't yet started.");
        }

        StringBuilder out = new StringBuilder();
        PlayerTurned e = game.canTurn(playerColor, turn, rollDice, out);

        if (e == null) {
            throw new IllegalArgumentException(String.format("This turn is illegal: %s", out.toString()));
        }

        e.setGameId(getId());
        apply(e);
    }

    private PlayerColor getPlayerColorById(UUID playerId) {
        if (playerWhite == playerId) {
            return PlayerColor.WHITE;
        }

        if (playerBlack == playerId) {
            return PlayerColor.BLACK;
        }

        return null;
    }

    private void when(NewGameSessionStarted e) {
        this.id = e.getGameId();
        this.playerWhite = e.getByPlayerId();
    }

    private void when(PartnerJoinedGameSession e) {
        this.playerBlack = e.getPlayerId();
    }

    private void when(GameStarted e) {
        isGameInProgress = true;
        game = new BackgammonGame(e.getConfig(), e.getDice());
    }

    private void when(PlayerTurned e) {
        game.turn(e.getPlayerColor(), e.getTurn());
    }

    public String getId() {
        return id;
    }
}
