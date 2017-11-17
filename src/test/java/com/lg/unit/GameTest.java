package com.lg.unit;

import com.lg.command.domain.Game;
import com.lg.command.domain.events.*;
import com.lg.command.domain.RollDice;
import com.lg.command.domain.services.RollDiceService;
import com.lg.command.domain.valueobjects.Dice;
import com.lg.command.es.DomainEvent;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.junit.Assert;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;

public class GameTest extends TestCase {
    public GameTest( String testName )
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
        Game target = Game.registerNewGame(gameId, player1Id);

        // Assert
        List<DomainEvent> events = target.flush();
        Assert.assertEquals(events.size(), 1);
        Assert.assertTrue(events.get(0) instanceof NewGameRegistered);

        NewGameRegistered e1 = (NewGameRegistered)events.get(0);

        Assert.assertEquals(e1.getPlayerId(), player1Id);
        Assert.assertEquals(e1.getGameId(), gameId);
    }

    public void test_When_join_to_registered_game_Should_join_and_start() throws Exception {
        // Arrange
        String gameId = UUID.randomUUID().toString();
        UUID player1Id = UUID.randomUUID();
        UUID player2Id = UUID.randomUUID();
        RollDice rollDice = mock(RollDice.class);

        Dice dice = new Dice(3, 6);
        when(rollDice.roll()).thenReturn(dice);

        Game target = Game.registerNewGame(gameId, player1Id);
        target.flush();

        // Act
        target.joinGame(player2Id, rollDice);

        // Assert
        List<DomainEvent> events = target.flush();
        Assert.assertEquals(events.size(), 2);
        Assert.assertTrue(events.get(0) instanceof PartnerJoined);
        Assert.assertTrue(events.get(1) instanceof GameStarted);

        PartnerJoined e1 = (PartnerJoined)events.get(0);
        GameStarted e2 = (GameStarted)events.get(1);

        verify(rollDice).roll();

        Assert.assertEquals(e1.getPlayerId(), player2Id);
        Assert.assertEquals(e1.getGameId(), gameId);
        Assert.assertEquals(e2.getDice1(), dice.getOne());
        Assert.assertEquals(e2.getDice2(), dice.getTwo());
        Assert.assertEquals(e2.getGameId(), gameId);

    }

//    public void test_When_player1_turns_after_game_start_Should_turn_happen() throws Exception {
//        // Arrange
//        String gameId = UUID.randomUUID().toString();
//        UUID player1Id = UUID.randomUUID();
//        UUID player2Id = UUID.randomUUID();
//        RollDice rollDice = mock(RollDice.class);
//
//        Dice dice = new Dice(5, 2);
//        when(rollDice.roll()).thenReturn(dice);
//
//        Game target = Game.registerNewGame(gameId, player1Id);
//        target.joinGame(player2Id, rollDice);
//        target.flush();
//
//        Move move = new Move(0, 2, 0, 5);
//
//        // Act
//        target.doMove(player1Id, new Move(move.getFrom1(), move.getSteps1(), move.getFrom2(), move.getSteps2()), rollDice);
//
//        // Assert
//        List<DomainEvent> events = target.flush();
//        Assert.assertEquals(events.size(), 1);
//        Assert.assertTrue(events.get(0) instanceof PlayerTurned);
//
//        PlayerTurned e1 = (PlayerTurned)events.get(0);
//
//        verify(rollDice, times(2)).roll();
//
//        Assert.assertEquals(e1.getPlayerId(), player1Id);
//        Assert.assertEquals(e1.getGameId(), gameId);
//        Assert.assertEquals(e1.getDice1(), dice.getOne());
//        Assert.assertEquals(e1.getDice2(), dice.getTwo());
//        Assert.assertEquals(e1.getMove().getFrom1(), move.getFrom1());
//        Assert.assertEquals(e1.getMove().getSteps1(), move.getSteps1());
//        Assert.assertEquals(e1.getMove().getFrom2(), move.getFrom2());
//        Assert.assertEquals(e1.getMove().getSteps2(), move.getSteps2());
//
//
//    }
}
