package com.lg.commandExecutors;

import com.lg.cqrs.ExecuteCommand;
import com.lg.domain.Game;
import com.lg.es.GameRepository;
import com.lg.messages.commands.RequestNewGame;

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
