package com.lg.infrastructure;

import com.lg.messages.queries.IQuery;

public interface IProcessQuery {
    Object process(IQuery query) throws ClassNotFoundException;
}
