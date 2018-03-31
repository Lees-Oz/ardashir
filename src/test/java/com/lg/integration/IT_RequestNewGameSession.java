package com.lg.integration;

import com.google.common.collect.ImmutableList;
import com.lg.command.domain.valueobjects.Dice;
import com.lg.command.domain.valueobjects.Move;
import com.lg.command.domain.valueobjects.Turn;
import com.lg.command.messages.DoTurn;
import com.lg.integration.infrastructure.Client;
import com.lg.query.messages.GetGameById;
import com.lg.query.messages.GetGameByIdResult;
import com.lg.query.messages.GetMyGame;
import com.lg.query.messages.GetMyGameResult;
import com.lg.command.messages.JoinGame;
import com.lg.command.messages.RequestNewGame;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

public class IT_RequestNewGameSession {
    private Client client = new Client();

    @Before
    public void before() throws InterruptedException {
        java.lang.Thread.sleep(1000); // This is hack, to avoid tests in maven being executed when app isn't started yet
    }

    @Test
    public void When_RequestNewGame_Should_game_be_available() throws Exception {
        // Arrange
        UUID playerId = UUID.randomUUID();
        RequestNewGame requestNewGameCmd = new RequestNewGame(playerId);

        // Act
        client.command(requestNewGameCmd);

        // Assert
        String gameId = client.query(GetMyGameResult.class, new GetMyGame(playerId), x -> {
            Assert.assertNotNull(x);
            Assert.assertNotNull(x.getGameId());
        }).getGameId();

        client.query(GetGameByIdResult.class, new GetGameById(gameId), x -> {
            Assert.assertEquals(x.getWhitePlayerId(), playerId);
            Assert.assertEquals(x.getBlackPlayerId(), null);
            Assert.assertEquals(x.getStatus(), "waitingPartner");
        });
    }

    @Test
    public void When_JoinGame_Should_game_be_available() throws Exception {
        // Arrange
        UUID player1Id = UUID.randomUUID();
        UUID player2Id = UUID.randomUUID();

        client.command(new RequestNewGame(player1Id));

        String gameId = client.query(GetMyGameResult.class, new GetMyGame(player1Id), null).getGameId();

        // Act
        client.command(new JoinGame(gameId, player2Id));

        // Assert
        client.query(GetMyGameResult.class, new GetMyGame(player2Id), x -> {
            Assert.assertNotNull(x);
            Assert.assertEquals(gameId, x.getGameId());
        });

        client.query(GetGameByIdResult.class, new GetGameById(gameId), x -> {
            Assert.assertEquals(x.getWhitePlayerId(), player1Id);
            Assert.assertEquals(x.getBlackPlayerId(), player2Id);
            Assert.assertEquals(x.getStatus(), "started");
        });
    }

    @Test
    public void When_doTurn_Should_update_game() throws Exception {
        // Arrange
        UUID player1Id = UUID.randomUUID();
        UUID player2Id = UUID.randomUUID();

        client.command(new RequestNewGame(player1Id));

        String gameId = client.query(GetMyGameResult.class, new GetMyGame(player1Id), null).getGameId();

        client.command(new JoinGame(gameId, player2Id));

        // Get my dice
        Dice dice = client.query(GetGameByIdResult.class, new GetGameById(gameId), x -> {
            Assert.assertNotNull(x);
            Assert.assertNotNull(x.getDice());
        }).getDice();

        // Act
        client.command(new DoTurn(
                gameId,
                player1Id,
                new Turn(ImmutableList.of(
                        new Move(0, dice.getOne()),
                        new Move(0, dice.getTwo())))));

        // Assert
        client.query(GetGameByIdResult.class, new GetGameById(gameId), x -> {
            Assert.assertNotNull(x);
            Assert.assertNotNull(x.getDice());
            Assert.assertNotNull(x.getBoardPoints());
        });
    }
}
