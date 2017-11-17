package com.lg.integration;

import com.lg.integration.infrastructure.Client;
import com.lg.query.messages.GetMyGame;
import com.lg.query.messages.GetMyGameResult;
import com.lg.command.messages.JoinGame;
import com.lg.command.messages.RequestNewGame;
import org.junit.Assert;
import org.junit.Test;

import java.util.UUID;

public class IT_RequestNewGame {
    private Client client = new Client();

    @Test
    public void When_RequestNewGame_Should_game_be_available() throws Exception {
        // Arrange
        RequestNewGame requestNewGameCmd = new RequestNewGame(UUID.randomUUID());

        // Act
        client.command(requestNewGameCmd);

        // Assert
        client.query(GetMyGameResult.class, new GetMyGame(requestNewGameCmd.getPlayerId()), x -> {
            Assert.assertNotNull(x);
            Assert.assertNotNull(x.getGameId());
        });
    }

    @Test
    public void When_JoinGame_Should_game_be_available() throws Exception {
        // Arrange
        RequestNewGame requestNewGameCmd = new RequestNewGame(UUID.randomUUID());
        client.command(requestNewGameCmd);

        String player1GameId = client.query(GetMyGameResult.class, new GetMyGame(requestNewGameCmd.getPlayerId()), null).getGameId();

        // Act
        JoinGame joinGameCmd = new JoinGame(player1GameId, UUID.randomUUID());
        client.command(joinGameCmd);

        // Assert
        client.query(GetMyGameResult.class, new GetMyGame(joinGameCmd.getPlayerId()), x -> {
            Assert.assertNotNull(x);
            Assert.assertEquals(player1GameId, x.getGameId());
        });
    }
}
