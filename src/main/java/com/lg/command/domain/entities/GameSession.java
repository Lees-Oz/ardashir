package com.lg.command.domain.entities;

import com.lg.command.domain.events.GameStarted;
import com.lg.command.domain.events.NewGameSessionStarted;
import com.lg.command.domain.events.PartnerJoinedGameSession;
import com.lg.command.domain.events.PlayerTurned;
import com.lg.command.domain.services.RollDice;
import com.lg.command.domain.valueobjects.*;
import com.lg.command.es.AggregateRoot;
import com.lg.command.es.DomainEvent;
import com.sun.javaws.exceptions.InvalidArgumentException;

import java.util.List;
import java.util.UUID;

public class GameSession extends AggregateRoot {
    private UUID playerWhite;
    private UUID playerBlack;

    private boolean isGameInProgress;

    private BackgammonBoard board;
    private PlayerColor nextPlayerColor;
    private Dice dice;

    private BackgammonConfig backgammonConfig;

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
        if (playerBlack == playerId) {
            return;
        }

        if (isGameInProgress) {
            throw new IllegalStateException("GameSession is already started.");
        }

        if (playerBlack != null) {
            throw new IllegalStateException("Another player is already joined.");
        }

        apply(new PartnerJoinedGameSession(id, playerId));
        apply(new GameStarted(id, gameConfig.provide(), rollDice.roll(), playerWhite, playerBlack, nextPlayerColor));
    }

    public void doTurn(UUID playerId, Turn turn, RollDice rollDice) throws Exception {
        PlayerColor playerColor = this.getPlayerColorById(playerId);
        if (playerColor == null) {
            throw new InvalidArgumentException(new String[]{"Such player doesn't exist."});
        }

        if (!this.isGameInProgress) {
            throw new IllegalStateException("GameSession isn't yet started.");
        }

        if (nextPlayerColor != playerColor && nextPlayerColor != null) {
            throw new IllegalStateException("It's another player's turn expected.");
        }

        BackgammonBoard resultBoard = board.tryTurn(playerColor, turn, new Steps(dice));

        if (resultBoard != null) {
            apply(new PlayerTurned(
                    id,
                    playerColor,
                    turn,
                    rollDice.roll(),
                    resultBoard.getPoints(),
                    nextPlayerColor.next()
            ));
        } else {
            throw new IllegalArgumentException("This turn isn't valid.");
        }
    }

    private void when(NewGameSessionStarted e) {
        id = e.getGameId();
        playerWhite = e.getByPlayerId();
    }

    private void when(PartnerJoinedGameSession e) {
        this.playerBlack = e.getPlayerId();
    }

    private void when(GameStarted e) {
        isGameInProgress = true;
        backgammonConfig = e.getGameConfig();
        board = new BackgammonBoard(e.getGameConfig());
        dice = e.getDice();
        nextPlayerColor = PlayerColor.WHITE;
    }

    private void when(PlayerTurned e) {
        board = board.asIfTurned(e.getPlayerColor(), e.getTurn());
        nextPlayerColor = e.getNextPlayerColor();
        dice = e.getDice();
    }

    private PlayerColor getPlayerColorById(UUID playerId) {
        if (playerWhite.equals(playerId)) {
            return PlayerColor.WHITE;
        }

        if (playerBlack.equals(playerId)) {
            return PlayerColor.BLACK;
        }

        return null;
    }

    public String getId() {
        return id;
    }
}
