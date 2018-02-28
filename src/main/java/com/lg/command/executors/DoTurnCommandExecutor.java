package com.lg.command.executors;

import com.lg.command.ExecuteCommand;
import com.lg.command.domain.entities.GameSession;
import com.lg.command.domain.services.RollDice;
import com.lg.command.es.GameRepository;
import com.lg.command.messages.DoTurn;

import javax.inject.Inject;

public class DoTurnCommandExecutor implements ExecuteCommand<DoTurn> {

    private GameRepository repo;
    private RollDice rollDice;

    @Inject
    public DoTurnCommandExecutor(GameRepository repo, RollDice rollDice) {
        this.repo = repo;
        this.rollDice = rollDice;
    }

    @Override
    public void execute(DoTurn command) throws Exception {
        GameSession game = repo.get(command.getGameId());
        game.doTurn(command.getPlayerId(), command.getTurn(), this.rollDice);
        this.repo.save(game);
    }
}
