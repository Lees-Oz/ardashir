package com.lg.controllers;

import com.lg.cqrs.ProcessQuery;
import com.lg.utils.SerializeJson;
import spark.Request;
import spark.Response;
import spark.Route;

import javax.inject.Inject;

public class QueryController implements Route {

    private SerializeJson serializer;
    private ProcessQuery queryProcessor;

    @Inject
    public QueryController(SerializeJson serializer, ProcessQuery queryProcessor) {
        this.serializer = serializer;
        this.queryProcessor = queryProcessor;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        Object result = queryProcessor.process(request.params(":name"), request.body());
        response.body(serializer.serialize(result));
        response.status(200);

        return response;
    }
}
