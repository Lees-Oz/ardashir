package com.lg.cqrs;

public interface ProcessCommand {
    void process(String name, String body) throws Exception;
}
