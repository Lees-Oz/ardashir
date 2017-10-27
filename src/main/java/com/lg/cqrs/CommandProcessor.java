package com.lg.cqrs;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.lg.utils.IJsonSerializer;

import java.lang.reflect.Method;

public class CommandProcessor implements IProcessCommand {
    private final String METHOD_NAME = "execute";

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
        Class<? extends ICommand> commandType = this.commandFinder.findCommandClass(name);
        ICommand command = (ICommand) this.serializer.deserialize(body, commandType);

        Class<? extends IExecuteCommand> executorType = this.commandFinder.findCommandExecutorClass(name);
        IExecuteCommand<? extends ICommand> executor = (IExecuteCommand<? extends ICommand>) this.injector.getInstance(executorType);

        Method executeMethod = executorType.getMethod(METHOD_NAME, commandType);
        executeMethod.invoke(executor, command);
    }
}
