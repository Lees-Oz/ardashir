package com.lg.command.executors;

import com.lg.command.ExecuteCommand;
import com.lg.command.domain.Game;
import com.lg.command.domain.RollDice;
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
        Game game = repo.get(command.getGameId());
        game.joinGame(command.getPlayerId(), this.rollDice);
        this.repo.save(game);
    }
}
