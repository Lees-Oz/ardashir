package com.lg.command;

public interface ExecuteCommand<T extends Command> {
    void execute(T command) throws Exception;
}
