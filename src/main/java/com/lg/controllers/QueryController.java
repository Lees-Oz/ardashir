package com.lg.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.lg.infrastructure.IProcessQuery;
import com.lg.messages.commands.ICommand;
import com.lg.messages.queries.GetGame;
import com.lg.messages.queries.IQuery;
import spark.Request;
import spark.Response;
import spark.Route;

import javax.inject.Inject;

public class QueryController implements Route {

    private IProcessQuery queryProcessor;

    @Inject
    public QueryController(IProcessQuery queryProcessor) {

        this.queryProcessor = queryProcessor;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        Object result;
        try {
            String queryName = request.params(":name");

            Class<? extends ICommand> queryClass = (Class<? extends ICommand>) Class.forName(GetGame.class.getPackage().getName() + "." + queryName);

            final ObjectMapper mapper = new ObjectMapper();
            final ObjectReader r = mapper.readerFor(queryClass);

            IQuery query = r.readValue(request.body());

            result = this.queryProcessor.process(query);
        }
        catch (Exception e) {
            response.body(e.toString());
            response.status(500);
            return response;
        }

        response.body(result.toString());
        response.status(200);
        return response;
    }
}
