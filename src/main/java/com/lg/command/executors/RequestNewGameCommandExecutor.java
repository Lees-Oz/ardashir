package com.lg.command.executors;

import com.lg.command.ExecuteCommand;
import com.lg.command.domain.entities.Game;
import com.lg.command.es.GameRepository;
import com.lg.command.messages.RequestNewGame;

import javax.inject.Inject;
import java.util.UUID;

public class RequestNewGameCommandExecutor implements ExecuteCommand<RequestNewGame> {

    private GameRepository repo;

    @Inject
    public RequestNewGameCommandExecutor(GameRepository repo) {
        this.repo = repo;
    }

    @Override
    public void execute(RequestNewGame command) throws Exception {
        Game newGame = Game.registerNewGame("game-" + UUID.randomUUID().toString(), command.getPlayerId());
        this.repo.save(newGame);
    }
}
