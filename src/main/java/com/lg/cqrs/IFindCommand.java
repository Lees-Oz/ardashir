package com.lg.cqrs;

public interface IFindCommand {
    Class<? extends ICommand> findCommandClass(String name) throws ClassNotFoundException;
    Class<? extends IExecuteCommand> findCommandExecutorClass(String name) throws ClassNotFoundException;
}
