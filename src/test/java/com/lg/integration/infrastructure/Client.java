package com.lg.integration.infrastructure;

import com.evanlennick.retry4j.CallExecutor;
import com.evanlennick.retry4j.config.RetryConfig;
import com.evanlennick.retry4j.config.RetryConfigBuilder;
import com.lg.command.Command;
import com.lg.query.Query;
import com.lg.query.QueryResult;
import com.lg.utils.JsonSerializer;
import com.lg.utils.SerializeJson;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClients;
import org.junit.Assert;

import java.io.IOException;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Callable;

public class Client {

    private RetryConfig retryConfig = new RetryConfigBuilder()
            .retryOnAnyException()
            .withMaxNumberOfTries(10)
            .withDelayBetweenTries(500, ChronoUnit.MILLIS)
            .withExponentialBackoff()
            .build();

    private CallExecutor executor = new CallExecutor(retryConfig).beforeNextTry(s -> System.out.println("Trying..."));

    private ResponseHandler<String> handler = new BasicResponseHandler();
    private SerializeJson serializer = new JsonSerializer();

    public <TResult extends QueryResult> TResult query(Class<TResult> type, Query query, TestQueryResult<TResult> test) throws IOException {
        HttpPut request = new HttpPut(String.format("http://localhost:4567/query/%s", query.getClass().getSimpleName()));
        request.setEntity(new StringEntity(serializer.serialize(query)));
        HttpClient client = HttpClients.createDefault();

        System.out.println("Query " + type.getSimpleName());

        Callable<TResult> callable = () -> {
            HttpResponse response = client.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();
            Assert.assertTrue(statusCode >= 200 && statusCode < 300);
            String body = handler.handleResponse(response);
            TResult r = (TResult) serializer.deserialize(body, type);

            if (test != null) {
                try {
                    test.test(r);
                } catch (AssertionError e) {
                    throw new Exception("Query assertion failed: " + e);
                }
            }
            return r;
        };

        TResult result = (TResult) (executor.execute(callable)).getResult();
        return result;
    }

    public void command(Command command) throws IOException {
        System.out.println("Command " + command.getClass().getSimpleName());

        HttpPost request = new HttpPost(String.format("http://localhost:4567/command/%s", command.getClass().getSimpleName()));
        request.setEntity(new StringEntity(serializer.serialize(command)));
        HttpResponse response = HttpClients.createDefault().execute(request);

        int statusCode = response.getStatusLine().getStatusCode();
        Assert.assertTrue(statusCode >= 200 && statusCode < 300);
    }
}
