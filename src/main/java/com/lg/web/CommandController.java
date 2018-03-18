package com.lg.web;

import com.lg.command.ProcessCommand;
import spark.Request;
import spark.Response;
import spark.Route;

import javax.inject.Inject;

public class CommandController implements Route  {

    private ProcessCommand commandProcessor;

    @Inject
    public CommandController(ProcessCommand commandProcessor) {
        this.commandProcessor = commandProcessor;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        this.commandProcessor.process(request.params(":name"), request.body());

        response.status(200);
        return "";
    }
}
