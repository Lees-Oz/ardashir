package com.lg.infrastructure;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.lg.commandExecutors.IExecuteCommand;
import com.lg.commandExecutors.RequestNewGameCommandExecutor;
import com.lg.messages.commands.ICommand;

public class CommandProcessor implements IProcessCommand {
    private Injector injector;

    @Inject
    public CommandProcessor(Injector injector) {
        this.injector = injector;
    }

    @Override
    public void process(ICommand command) throws Exception {
        Class t = command.getClass();
        Class executorType = Class.forName(RequestNewGameCommandExecutor.class.getPackage().getName() + "." + t.getSimpleName() + "CommandExecutor");

        IExecuteCommand<ICommand> executor = (IExecuteCommand<ICommand>) this.injector.getInstance(executorType);

        executor.execute(command);
    }
}
