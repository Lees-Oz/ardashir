package com.lg.cqrs;

public interface FindCommand {
    Class<? extends Command> findCommandClass(String name) throws ClassNotFoundException;
    Class<? extends ExecuteCommand> findCommandExecutorClass(String name) throws ClassNotFoundException;
}
