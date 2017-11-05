package com.lg.cqrs;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.lg.utils.SerializeJson;

import java.lang.reflect.Method;

public class CommandProcessor implements ProcessCommand {
    private final String METHOD_NAME = "execute";

    private FindCommand commandFinder;
    private SerializeJson serializer;
    private Injector injector;

    @Inject
    public CommandProcessor(FindCommand commandFinder, SerializeJson serializer, Injector injector) {
        this.commandFinder = commandFinder;
        this.serializer = serializer;
        this.injector = injector;
    }

    @Override
    public void process(String name, String body) throws Exception {
        Class<? extends Command> commandType = this.commandFinder.findCommandClass(name);
        Command command = (Command) this.serializer.deserialize(body, commandType);

        Class<? extends ExecuteCommand> executorType = this.commandFinder.findCommandExecutorClass(name);
        ExecuteCommand<? extends Command> executor = (ExecuteCommand<? extends Command>) this.injector.getInstance(executorType);

        Method executeMethod = executorType.getMethod(METHOD_NAME, commandType);
        executeMethod.invoke(executor, command);
    }
}
