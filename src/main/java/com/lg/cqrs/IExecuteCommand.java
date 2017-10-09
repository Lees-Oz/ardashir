package com.lg.cqrs;

public interface IExecuteCommand<T extends ICommand> {
    void execute(ICommand command) throws Exception;
}
