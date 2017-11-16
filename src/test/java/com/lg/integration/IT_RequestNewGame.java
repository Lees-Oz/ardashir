package com.lg.integration;

import com.lg.messages.commands.JoinGame;
import com.lg.messages.commands.RequestNewGame;
import com.lg.messages.queries.GetMyGame;
import com.lg.messages.queries.GetMyGameResult;
import com.lg.utils.JsonSerializer;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClients;
import org.junit.Assert;
import org.junit.Test;

import java.util.UUID;

public class IT_RequestNewGame {

    private JsonSerializer serializer = new JsonSerializer();
    private ResponseHandler<String> handler = new BasicResponseHandler();

    @Test
    public void When_command_request_Should_process() throws Exception {
        // Arrange
        HttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://localhost:4567/command/RequestNewGame");
        httpPost.setEntity(new StringEntity(serializer.serialize(new RequestNewGame(UUID.randomUUID()))));

        // Act
        HttpResponse response = httpClient.execute(httpPost);

        // Assert
        Assert.assertNotNull(response.getEntity().getContent().toString());
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    }

    @Test
    public void When_join_request_Should_process() throws Exception {


        // Arrange
        HttpClient httpClient = HttpClients.createDefault();

        HttpPost requestNewGameRequest = new HttpPost("http://localhost:4567/command/RequestNewGame");
        UUID player1Id = UUID.randomUUID();
        requestNewGameRequest.setEntity(new StringEntity(serializer.serialize(new RequestNewGame(player1Id))));
        httpClient.execute(requestNewGameRequest);

        HttpPut getMyGameReq = new HttpPut("http://localhost:4567/query/GetMyGame");
        getMyGameReq.setEntity(new StringEntity(serializer.serialize(new GetMyGame(player1Id))));
        HttpResponse response = httpClient.execute(getMyGameReq);
        String body = handler.handleResponse(response);
        GetMyGameResult result = (GetMyGameResult) serializer.deserialize(body, GetMyGameResult.class);
        String gameIdPlayer1 = result.getGameId();

        // Act
        HttpPost httpPost = new HttpPost("http://localhost:4567/command/JoinGame");
        UUID player2Id = UUID.randomUUID();
        httpPost.setEntity(new StringEntity(serializer.serialize(new JoinGame(gameIdPlayer1, player2Id))));
        HttpResponse response2 = httpClient.execute(httpPost);

        // Assert
        Assert.assertEquals(response2.getStatusLine().getStatusCode(), 200);

        HttpPut getMyGameReq2 = new HttpPut("http://localhost:4567/query/GetMyGame");
        getMyGameReq2.setEntity(new StringEntity(serializer.serialize(new GetMyGame(player2Id))));
        HttpResponse response3 = HttpClients.createDefault().execute(getMyGameReq2);
        String body2 = handler.handleResponse(response3);
        GetMyGameResult result2 = (GetMyGameResult) serializer.deserialize(body2, GetMyGameResult.class);
        String gameIdPlayer2 = result2.getGameId();

        Assert.assertEquals(gameIdPlayer1, gameIdPlayer2);
    }

    @Test
    public void When_query_request_Should_process() throws Exception {
        // Arrange
        HttpClient httpClient = HttpClients.createDefault();
        HttpPut httpPut = new HttpPut("http://localhost:4567/query/GetMyGame");
        httpPut.setEntity(new StringEntity(serializer.serialize(new GetMyGame(UUID.randomUUID()))));

        // Act
        HttpResponse response = httpClient.execute(httpPut);

        // Assert
        Assert.assertNotNull(response.getEntity().getContent().toString());
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    }
}
