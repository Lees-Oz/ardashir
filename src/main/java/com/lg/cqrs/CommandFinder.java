package com.lg.cqrs;

import com.lg.commandExecutors.RequestNewGameCommandExecutor;
import com.lg.messages.commands.RequestNewGame;

public class CommandFinder implements FindCommand {
    @SuppressWarnings("unchecked")
    public Class<? extends Command> findCommandClass(String name) throws ClassNotFoundException {
        String commandClassName = String.format("%s.%s", RequestNewGame.class.getPackage().getName(), name);
        return (Class<? extends Command>) Class.forName(commandClassName);
    }

    @SuppressWarnings("unchecked")
    public Class<? extends ExecuteCommand> findCommandExecutorClass(String name) throws ClassNotFoundException {
        String executorClassName = String.format("%s.%sCommandExecutor", RequestNewGameCommandExecutor.class.getPackage().getName(), name);
        return (Class<? extends ExecuteCommand<? extends Command>>) Class.forName(executorClassName);
    }
}
