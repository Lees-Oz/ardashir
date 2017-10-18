package com.lg.integration;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class IT_RequestNewGame {

    @Test
    public void When_command_request_Should_process() throws Exception {
        // Arrange
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://localhost:4567/command/RequestNewGame");
        httpPost.setEntity(new StringEntity("{\"gameId\":\"" + UUID.randomUUID().toString() + "\"}"));

        // Act
        CloseableHttpResponse response1 = httpClient.execute(httpPost);

        // Assert
        assertNotNull(response1.getEntity().getContent().toString());
        assertEquals(response1.getStatusLine().getStatusCode(), 200);
    }
}
