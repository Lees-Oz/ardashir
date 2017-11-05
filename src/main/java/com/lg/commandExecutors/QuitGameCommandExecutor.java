package com.lg.commandExecutors;

import com.lg.cqrs.ExecuteCommand;
import com.lg.es.GameRepo;
import com.lg.messages.commands.QuitGame;
import javax.inject.Inject;


public class QuitGameCommandExecutor implements ExecuteCommand<QuitGame> {

    @Inject
    public QuitGameCommandExecutor(GameRepo repo) {
    }

    @Override
    public void execute(QuitGame command) {
        System.out.println(command.toString());
    }
}
