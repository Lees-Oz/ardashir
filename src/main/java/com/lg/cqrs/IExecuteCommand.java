package com.lg.cqrs;

public interface IExecuteCommand<T extends ICommand> {
    void execute(T command) throws Exception;
}
