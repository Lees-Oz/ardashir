package com.lg.cqrs;

import com.lg.commandExecutors.RequestNewGameCommandExecutor;
import com.lg.messages.commands.RequestNewGame;

public class CommandFinder implements IFindCommand {
    public Class<? extends ICommand> findCommandClass(String name) throws ClassNotFoundException {
        final String commandClassName = String.format("%s.%s", RequestNewGame.class.getPackage().getName(), name);
        return (Class<? extends ICommand>) Class.forName(commandClassName);
    }

    public Class<? extends IExecuteCommand> findCommandExecutorClass(String name) throws ClassNotFoundException {
        final String executorClassName = String.format("%s.%sCommandExecutor", RequestNewGameCommandExecutor.class.getPackage().getName(), name);
        return (Class<? extends IExecuteCommand<? extends ICommand>>) Class.forName(executorClassName);
    }
}
