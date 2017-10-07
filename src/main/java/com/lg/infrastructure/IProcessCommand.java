package com.lg.infrastructure;

import com.lg.messages.commands.ICommand;

public interface IProcessCommand {
    void process(ICommand command) throws Exception;
}
