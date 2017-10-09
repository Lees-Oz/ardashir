package com.lg.messages.commands;

import com.lg.cqrs.ICommand;

import java.util.UUID;

public class QuitGame implements ICommand {
    UUID gameId;
}
