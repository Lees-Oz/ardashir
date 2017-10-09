package com.lg.cqrs;

public interface IProcessQuery {
    IQueryResult process(String name, String body) throws Exception;
}
