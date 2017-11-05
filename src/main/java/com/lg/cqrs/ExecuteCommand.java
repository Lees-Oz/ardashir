package com.lg.cqrs;

public interface ExecuteCommand<T extends Command> {
    void execute(T command) throws Exception;
}
