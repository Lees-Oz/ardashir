package com.lg.messages.queries;

import com.lg.cqrs.IQuery;

import java.util.UUID;

public class GetGame implements IQuery {
    public UUID gameId;
}
