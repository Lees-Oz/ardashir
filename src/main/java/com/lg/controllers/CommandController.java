package com.lg.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.lg.infrastructure.IProcessCommand;
import com.lg.messages.commands.ICommand;
import com.lg.messages.commands.RequestNewGame;
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
        try {
            String commandName = request.params(":name");

            Class<? extends ICommand> commandClass = (Class<? extends ICommand>) Class.forName(RequestNewGame.class.getPackage().getName() + "." + commandName);

            final ObjectMapper mapper = new ObjectMapper();
            final ObjectReader r = mapper.readerFor(commandClass);

            ICommand command = r.readValue(request.body());

            commandProcessor.process(command);
        }
        catch (Exception e) {
            response.body(e.toString());
            response.status(500);
            return response;
        }

        response.status(200);
        return response;
    }
}
