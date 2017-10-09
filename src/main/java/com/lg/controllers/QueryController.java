package com.lg.controllers;

import com.lg.cqrs.IProcessQuery;
import com.lg.utils.IJsonSerializer;
import spark.Request;
import spark.Response;
import spark.Route;

import javax.inject.Inject;

public class QueryController implements Route {

    private IJsonSerializer serializer;
    private IProcessQuery queryProcessor;

    @Inject
    public QueryController(IJsonSerializer serializer, IProcessQuery queryProcessor) {
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
