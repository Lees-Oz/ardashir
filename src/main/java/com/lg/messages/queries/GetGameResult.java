package com.lg.messages.queries;

import com.lg.cqrs.IQueryResult;

import java.util.UUID;

public class GetGameResult implements IQueryResult {
    public UUID gameId;
}
