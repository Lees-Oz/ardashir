package com.lg.controllers;

import com.lg.cqrs.IProcessCommand;
import spark.Request;
import spark.Response;
import spark.Route;

import javax.inject.Inject;

public class CommandController implements Route  {

    private IProcessCommand commandProcessor;

    @Inject
    public CommandController(IProcessCommand commandProcessor) {
        this.commandProcessor = commandProcessor;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        this.commandProcessor.process(request.params(":name"), request.body());

        response.status(200);
        return response;
    }
}
