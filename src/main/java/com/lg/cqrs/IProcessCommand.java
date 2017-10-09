package com.lg.cqrs;

public interface IProcessCommand {
    void process(String name, String body) throws Exception;
}
