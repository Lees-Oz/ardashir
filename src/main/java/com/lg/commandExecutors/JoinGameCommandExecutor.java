package com.lg.commandExecutors;

import com.lg.cqrs.ExecuteCommand;
import com.lg.domain.Game;
import com.lg.domain.RollDice;
import com.lg.es.GameRepository;
import com.lg.messages.commands.JoinGame;

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
