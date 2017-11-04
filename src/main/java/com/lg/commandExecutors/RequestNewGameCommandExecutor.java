package com.lg.commandExecutors;

import com.lg.cqrs.IExecuteCommand;
import com.lg.domain.Game;
import com.lg.es.GameRepo;
import com.lg.messages.commands.RequestNewGame;

import javax.inject.Inject;
import java.util.UUID;

public class RequestNewGameCommandExecutor implements IExecuteCommand<RequestNewGame> {

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
