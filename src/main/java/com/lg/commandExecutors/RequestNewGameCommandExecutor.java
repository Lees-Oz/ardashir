package com.lg.commandExecutors;

import com.lg.es.GameRepo;
import com.lg.messages.commands.RequestNewGame;

import javax.inject.Inject;

import static java.lang.System.out;

public class RequestNewGameCommandExecutor implements IExecuteCommand<RequestNewGame>{

    private GameRepo repo;

    @Inject
    public RequestNewGameCommandExecutor(GameRepo repo) {
        this.repo = repo;
    }

    @Override
    public void execute(RequestNewGame command) {
        out.println(command.toString());
    }
}
