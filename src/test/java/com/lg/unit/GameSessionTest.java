package com.lg.unit;

import com.lg.command.domain.entities.GameSession;
import com.lg.command.domain.events.*;
import com.lg.command.domain.events.PlayerTurned;
import com.lg.command.domain.services.RollDice;
import com.lg.command.domain.services.RollDiceService;
import com.lg.command.domain.valueobjects.*;
import com.lg.command.es.DomainEvent;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.junit.Assert;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;

public class GameSessionTest extends TestCase {
    public GameSessionTest(String testName )
    {
        super( testName );
    }

    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    public void test_When_registered_Should_be_registered() throws Exception {
        // Arrange
        String gameId = UUID.randomUUID().toString();
        UUID player1Id = UUID.randomUUID();

        RollDiceService rollDice = mock(RollDiceService.class);

        // Act
        GameSession target = GameSession.startNewGameSession(gameId, player1Id);

        // Assert
        List<DomainEvent> events = target.flush();
        Assert.assertEquals(events.size(), 1);
        Assert.assertTrue(events.get(0) instanceof NewGameSessionStarted);

        NewGameSessionStarted e1 = (NewGameSessionStarted)events.get(0);

        Assert.assertEquals(e1.getByPlayerId(), player1Id);
        Assert.assertEquals(e1.getGameId(), gameId);
    }

    public void test_When_join_to_registered_game_Should_join_and_start() throws Exception {
        // Arrange
        String gameId = UUID.randomUUID().toString();
        UUID player1Id = UUID.randomUUID();
        UUID player2Id = UUID.randomUUID();
        RollDice rollDice = mock(RollDice.class);
        ProvideBackgammonConfig provideConfig = mock(ProvideBackgammonConfig.class);


        Dice dice = new Dice(3, 6);
        when(rollDice.roll()).thenReturn(dice);
        when(provideConfig.provide()).thenReturn(DefaultBackgammonConfig.get());

        GameSession target = GameSession.startNewGameSession(gameId, player1Id);
        target.flush();

        // Act
        target.joinGameSession(player2Id, rollDice, provideConfig);

        // Assert
        List<DomainEvent> events = target.flush();
        Assert.assertEquals(events.size(), 2);
        Assert.assertTrue(events.get(0) instanceof PartnerJoinedGameSession);
        Assert.assertTrue(events.get(1) instanceof GameStarted);

        PartnerJoinedGameSession e1 = (PartnerJoinedGameSession)events.get(0);
        GameStarted e2 = (GameStarted)events.get(1);

        verify(rollDice).roll();

        Assert.assertEquals(e1.getPlayerId(), player2Id);
        Assert.assertEquals(e1.getGameId(), gameId);
        Assert.assertEquals(e2.getDice(), dice);
        Assert.assertEquals(e2.getWhitePlayerId(), player1Id);
        Assert.assertEquals(e2.getBlackPlayerId(), player2Id);
    }

    public void test_When_player1_turns_after_game_start_Should_turn_happen() throws Exception {
        // Arrange
        String gameId = UUID.randomUUID().toString();
        UUID player1Id = UUID.randomUUID();
        UUID player2Id = UUID.randomUUID();
        RollDice rollDice = mock(RollDice.class);
        ProvideBackgammonConfig provideConfig = mock(ProvideBackgammonConfig.class);

        Dice dice = new Dice(5, 2);
        when(rollDice.roll()).thenReturn(dice);
        when(provideConfig.provide()).thenReturn(DefaultBackgammonConfig.get());

        GameSession target = GameSession.startNewGameSession(gameId, player1Id);

        target.joinGameSession(player2Id, rollDice, provideConfig);
        target.flush();

        Turn turn = new Turn(new Move[] { new Move(0, dice.getOne()), new Move(0, dice.getTwo())});

        // Act
        target.doTurn(player1Id, turn, rollDice);

        // Assert
        List<DomainEvent> events = target.flush();
        Assert.assertEquals(events.size(), 1);
        Assert.assertTrue(events.get(0) instanceof PlayerTurned);

        PlayerTurned e1 = (PlayerTurned)events.get(0);

        verify(rollDice, times(2)).roll();

        Assert.assertEquals(e1.getGameId(), gameId);
        Assert.assertEquals(e1.getPlayerColor(), PlayerColor.WHITE);
        Assert.assertEquals(e1.getTurn(), turn);
    }
}
