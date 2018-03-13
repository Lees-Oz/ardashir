package com.lg.unit;

import com.lg.command.domain.entities.GameSession;
import com.lg.command.domain.events.GameStarted;
import com.lg.command.domain.events.NewGameSessionStarted;
import com.lg.command.domain.events.PartnerJoinedGameSession;
import com.lg.command.domain.events.PlayerTurned;
import com.lg.command.domain.services.RollDice;
import com.lg.command.domain.valueobjects.*;
import com.lg.command.es.DomainEvent;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.Arrays;
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

        // Act
        GameSession target = GameSession.startNewGameSession(gameId, player1Id);

        // Assert
        List<DomainEvent> events = target.flush();
        Assert.assertEquals(events.size(), 1);

        NewGameSessionStarted e1 = (NewGameSessionStarted)events.get(0);
        Assert.assertNotNull(e1);

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
        BackgammonConfig config = new BackgammonConfig(15, 24, 0, 12, 6);
        when(provideConfig.provide()).thenReturn(config);

        GameSession target = GameSession.startNewGameSession(gameId, player1Id);
        target.flush();

        // Act
        target.joinGameSession(player2Id, rollDice, provideConfig);

        // Assert
        List<DomainEvent> events = target.flush();

        Assert.assertEquals(events.size(), 2);

        PartnerJoinedGameSession e1 = (PartnerJoinedGameSession)events.get(0);
        GameStarted e2 = (GameStarted)events.get(1);

        Assert.assertNotNull(e1);
        Assert.assertEquals(e1.getPlayerId(), player2Id);
        Assert.assertEquals(e1.getGameId(), gameId);

        Assert.assertNotNull(e2);
        Assert.assertEquals(e2.getDice(), dice);
        Assert.assertEquals(e2.getWhitePlayerId(), player1Id);
        Assert.assertEquals(e2.getBlackPlayerId(), player2Id);
        Assert.assertEquals(e2.getGameConfig(), config);
        Assert.assertEquals(e2.getNextPlayerColor(), PlayerColor.WHITE);

        verify(rollDice).roll();
    }

    public void test_When_player1_valid_turns_after_game_start_Should_turn_happen() throws Exception {
        // Arrange
        String gameId = UUID.randomUUID().toString();
        UUID player1Id = UUID.randomUUID();
        UUID player2Id = UUID.randomUUID();

        RollDice rollDice = mock(RollDice.class);
        Dice dice = new Dice(5, 2);
        when(rollDice.roll()).thenReturn(dice);

        ProvideBackgammonConfig provideConfig = mock(ProvideBackgammonConfig.class);
        BackgammonConfig backgammonConfig = new BackgammonConfig(15, 24, 0, 12, 6);
        when(provideConfig.provide()).thenReturn(backgammonConfig);

        GameSession target = GameSession.startNewGameSession(gameId, player1Id);
        target.joinGameSession(player2Id, rollDice, provideConfig);
        target.flush();

        // Act
        Turn turn = new Turn(new Move[] {
                new Move(0, dice.getOne()),
                new Move(0, dice.getTwo())});
        target.doTurn(player1Id, turn, rollDice);

        // Assert
        List<DomainEvent> events = target.flush();
        Assert.assertEquals(events.size(), 1);

        PlayerTurned e1 = (PlayerTurned)events.get(0);
        Assert.assertNotNull(e1);
        Assert.assertEquals(e1.getGameId(), gameId);
        Assert.assertEquals(e1.getPlayerColor(), PlayerColor.WHITE);
        Assert.assertEquals(e1.getTurn(), turn);
        Assert.assertEquals(e1.getNextPlayerColor(), PlayerColor.BLACK);

        ArrayList<BoardPoint> resultPoints = new ArrayList<>(Arrays.asList(e1.getBoardPoints()));
        new ArrayList<>(Arrays.asList(
                new BoardPoint(0, 13, PlayerColor.WHITE),
                new BoardPoint(1, 0, null),
                new BoardPoint(2, 1, PlayerColor.WHITE),
                new BoardPoint(3, 0, null),
                new BoardPoint(4, 0, null),
                new BoardPoint(5, 1, PlayerColor.WHITE),
                new BoardPoint(6, 0, null),
                new BoardPoint(7, 0, null),
                new BoardPoint(8, 0, null),
                new BoardPoint(9, 0, null),
                new BoardPoint(10, 0, null),
                new BoardPoint(11, 0, null),
                new BoardPoint(12, 15, PlayerColor.BLACK),
                new BoardPoint(13, 0, null),
                new BoardPoint(14, 0, null),
                new BoardPoint(15, 0, null),
                new BoardPoint(16, 0, null),
                new BoardPoint(17, 0, null),
                new BoardPoint(18, 0, null),
                new BoardPoint(19, 0, null),
                new BoardPoint(20, 0, null),
                new BoardPoint(21, 0, null),
                new BoardPoint(22, 0, null),
                new BoardPoint(23, 0, null)))
        .forEach(x -> Assert.assertTrue(resultPoints.contains(x)));

        Assert.assertEquals(e1.getBoardPoints().length, backgammonConfig.getPointsCount());

        verify(rollDice, times(2)).roll();
    }
}

