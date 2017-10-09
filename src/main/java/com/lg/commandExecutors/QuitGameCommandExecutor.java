package com.lg.commandExecutors;

import com.lg.cqrs.ICommand;
import com.lg.cqrs.IExecuteCommand;
import com.lg.es.GameRepo;
import com.lg.messages.commands.QuitGame;

import javax.inject.Inject;

import static java.lang.System.out;

public class QuitGameCommandExecutor implements IExecuteCommand<QuitGame> {

    @Inject
    public QuitGameCommandExecutor(GameRepo repo) {
    }

    @Override
    public void execute(ICommand command) {
        out.println(command.toString());
    }
}
