package com.lg.cqrs;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.lg.utils.IJsonSerializer;

public class CommandProcessor implements IProcessCommand {
    private IFindCommand commandFinder;
    private IJsonSerializer serializer;
    private Injector injector;

    @Inject
    public CommandProcessor(IFindCommand commandFinder, IJsonSerializer serializer, Injector injector) {
        this.commandFinder = commandFinder;
        this.serializer = serializer;
        this.injector = injector;
    }

    @Override
    public void process(String name, String body) throws Exception {
        Class<? extends ICommand> commandClass = this.commandFinder.findCommandClass(name);
        ICommand command = (ICommand) this.serializer.deserialize(body, commandClass);

        Class executorClass = this.commandFinder.findCommandExecutorClass(name);

        IExecuteCommand<?> executor = (IExecuteCommand<?>) this.injector.getInstance(executorClass);

        executor.execute(command);
    }
}
