package com.lg.commandExecutors;

import com.lg.cqrs.IExecuteCommand;
import com.lg.domain.Game;
import com.lg.es.GameRepo;
import com.lg.messages.commands.RequestNewGame;

import javax.inject.Inject;

public class RequestNewGameCommandExecutor implements IExecuteCommand<RequestNewGame> {

    private GameRepo repo;

    @Inject
    public RequestNewGameCommandExecutor(GameRepo repo) {
        this.repo = repo;
    }

    @Override
    public void execute(RequestNewGame command) {
        // Validate gameId is unique

        Game newGame = Game.startNewGame(command.getGameId().toString(), command.getPlayerId());
        this.repo.save(newGame);
    }
}
