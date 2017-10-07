package com.lg.commandExecutors;

import com.lg.messages.commands.ICommand;

public interface IExecuteCommand<T extends ICommand> {
    void execute(T command) throws Exception;
}
