package com.lg.integration;

import com.lg.cqrs.Command;
import com.lg.cqrs.Query;
import com.lg.cqrs.QueryResult;
import com.lg.utils.JsonSerializer;
import com.lg.utils.SerializeJson;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClients;
import org.junit.Assert;

import java.io.IOException;

public class Client {
    private ResponseHandler<String> handler = new BasicResponseHandler();
    private SerializeJson serializer = new JsonSerializer();

    public <TResult extends QueryResult> TResult query(Class<TResult> type, Query query, TestQueryResult<TResult> test) throws IOException {
        HttpPut request = new HttpPut(String.format("http://localhost:4567/query/%s", query.getClass().getSimpleName()));
        request.setEntity(new StringEntity(serializer.serialize(query)));
        HttpResponse response = HttpClients.createDefault().execute(request);

        int statusCode = response.getStatusLine().getStatusCode();
        Assert.assertTrue(statusCode >= 200 && statusCode < 300);

        String body = handler.handleResponse(response);
        TResult result = (TResult) serializer.deserialize(body, type);

        if (test != null) {
            test.test(result);
        }

        return result;
    }

    public void command(Command command) throws IOException {
        HttpPost request = new HttpPost(String.format("http://localhost:4567/command/%s", command.getClass().getSimpleName()));
        request.setEntity(new StringEntity(serializer.serialize(command)));
        HttpResponse response = HttpClients.createDefault().execute(request);

        int statusCode = response.getStatusLine().getStatusCode();
        Assert.assertTrue(statusCode >= 200 && statusCode < 300);
    }
}
