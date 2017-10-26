package com.lg.commandExecutors;

import com.lg.cqrs.ICommand;
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
    public void execute(ICommand command) {
        RequestNewGame cmd = (RequestNewGame) command; // Let's see if there's a way to have RequestNewGame type in argument already

        // Validate gameId is unique

        Game newGame = Game.StartNewGame(cmd.getGameId().toString(), cmd.getPlayerId());

        this.repo.save(newGame);
    }

}
