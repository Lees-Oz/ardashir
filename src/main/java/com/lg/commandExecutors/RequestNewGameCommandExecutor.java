package com.lg.commandExecutors;

import com.lg.cqrs.ExecuteCommand;
import com.lg.domain.Game;
import com.lg.es.GameRepo;
import com.lg.messages.commands.RequestNewGame;

import javax.inject.Inject;
import java.util.UUID;

public class RequestNewGameCommandExecutor implements ExecuteCommand<RequestNewGame> {

    private GameRepo repo;

    @Inject
    public RequestNewGameCommandExecutor(GameRepo repo) {
        this.repo = repo;
    }

    @Override
    public void execute(RequestNewGame command) throws Exception {
        Game newGame = Game.registerNewGame(UUID.randomUUID().toString(), command.getPlayerId());
        this.repo.save(newGame);
    }
}
