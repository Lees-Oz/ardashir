package com.lg.command.executors;

import com.lg.command.ExecuteCommand;
import com.lg.command.domain.entities.GameSession;
import com.lg.command.domain.services.RollDice;
import com.lg.command.domain.valueobjects.ProvideBackgammonConfig;
import com.lg.command.es.GameRepository;
import com.lg.command.messages.JoinGame;

import javax.inject.Inject;

public class JoinGameCommandExecutor implements ExecuteCommand<JoinGame> {

    private GameRepository repo;
    private RollDice rollDice;

    @Inject
    public JoinGameCommandExecutor(GameRepository repo, RollDice rollDice) {
        this.repo = repo;
        this.rollDice = rollDice;
    }

    @Override
    public void execute(JoinGame command) throws Exception {
        GameSession game = repo.get(command.getGameId());
        game.joinGameSession(command.getPlayerId(), this.rollDice);
        this.repo.save(game);
    }
}
