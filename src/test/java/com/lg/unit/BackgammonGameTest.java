package com.lg.unit;

import com.lg.command.domain.events.PlayerTurned;
import com.lg.command.domain.services.RollDice;
import com.lg.command.domain.valueobjects.*;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.junit.Assert;

import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BackgammonGameTest extends TestCase {

    public BackgammonGameTest(String testName )
    {
        super( testName );
    }

    public static Test suite()
    {
        return new TestSuite( BackgammonGameTest.class );
    }

    public void test_When_canMove_in_start_position_Should_return_correct_move_validity() {
        // Arrange
        UUID player1Id = UUID.randomUUID();

        BackgammonConfig config = DefaultBackgammonConfig.get();
        BackgammonGame target =  new BackgammonGame(config, new Dice(3, 6));

        RollDice rollDice = mock(RollDice.class);
        Dice nextDice = new Dice(1, 3);
        when(rollDice.roll()).thenReturn(nextDice);

        // Act & Assert
        Turn turn = new Turn(new Move[]{new Move(0, 6), new Move(0, 3)});
        PlayerTurned e = target.canTurn(PlayerColor.WHITE, turn, rollDice, new StringBuilder());
        Assert.assertNotNull(e);
        Assert.assertEquals(e.getPlayerColor(), PlayerColor.WHITE);
        Assert.assertEquals(e.getTurn(), turn);
        Assert.assertEquals(e.getDice(), nextDice);

        //Assert.assertNull(target.canTurn(PlayerColor.WHITE, new Turn(new Move[] { new Move(10, 3), new Move(0, 6)}), rollDice, new StringBuilder()));
        //Assert.assertNull(target.canTurn(PlayerColor.WHITE, new Turn(new Move[] { new Move(0, 12), new Move(0, 6)}), rollDice, new StringBuilder()));
    }
//
//    public void test_When_canMove_black_player_has_obstacles_Should_return_correct_move_validity() {
//        // Arrange
//        RollDiceService rollDice = mock(RollDiceService.class);
//        Dice dice = new Dice(3, 6);
//        when(rollDice.roll()).thenReturn(dice);
//        BackgammonGame target = new BackgammonGame(dice);
//
//        int blackPlayerStartPos = 13;
//
//        StringBuilder out = new StringBuilder();
//
//        target.turn(PlayerColor.WHITE, new Turn(new Move[] { new Move(0, blackPlayerStartPos + 2), new Move(0, 3)}), rollDice);
//
//        // Act & Assert
//        Assert.assertNotNull(target.canTurn(PlayerColor.BLACK, new Turn(new Move[] { new Move(blackPlayerStartPos, 0), new Move(blackPlayerStartPos, 0)}), rollDice, out));
//
//        Assert.assertNotNull(target.canTurn(PlayerColor.BLACK, new Turn(new Move[] { new Move(blackPlayerStartPos, 4), new Move(blackPlayerStartPos + 4, 2)}), rollDice, out));
//        Assert.assertNotNull(target.canTurn(PlayerColor.BLACK, new Turn(new Move[] { new Move(blackPlayerStartPos + 4, 2), new Move(blackPlayerStartPos, 4)}), rollDice, out));
//
//        Assert.assertNotNull(target.canTurn(PlayerColor.BLACK, new Turn(new Move[] { new Move(blackPlayerStartPos, 3), new Move(blackPlayerStartPos, 6)}), rollDice, out));
//        Assert.assertNotNull(target.canTurn(PlayerColor.BLACK, new Turn(new Move[] { new Move(blackPlayerStartPos, 6), new Move(blackPlayerStartPos, 3)}), rollDice, out));
//
//        Assert.assertNotNull(target.canTurn(PlayerColor.BLACK, new Turn(new Move[] { new Move(blackPlayerStartPos, 14), new Move(blackPlayerStartPos, 4)}), rollDice, out));
//        Assert.assertNotNull(target.canTurn(PlayerColor.BLACK, new Turn(new Move[] { new Move(blackPlayerStartPos, 4), new Move(blackPlayerStartPos, 14)}), rollDice, out));
//
//
//        Assert.assertNull(target.canTurn(PlayerColor.BLACK, new Turn(new Move[] { new Move(blackPlayerStartPos, 2), new Move(blackPlayerStartPos, 4)}), rollDice, out));
//        Assert.assertNull(target.canTurn(PlayerColor.BLACK, new Turn(new Move[] { new Move(blackPlayerStartPos, 4), new Move(blackPlayerStartPos, 2)}), rollDice, out));
//
//        Assert.assertNull(target.canTurn(PlayerColor.BLACK, new Turn(new Move[] { new Move(blackPlayerStartPos, 2), new Move(blackPlayerStartPos + 2, 4)}), rollDice, out));
//        Assert.assertNull(target.canTurn(PlayerColor.BLACK, new Turn(new Move[] { new Move(blackPlayerStartPos + 2, 4), new Move(blackPlayerStartPos, 2)}), rollDice, out));
//
//        Assert.assertNull(target.canTurn(PlayerColor.BLACK, new Turn(new Move[] { new Move(blackPlayerStartPos, 15), new Move(blackPlayerStartPos, 4)}), rollDice, out));
//        Assert.assertNull(target.canTurn(PlayerColor.BLACK, new Turn(new Move[] { new Move(blackPlayerStartPos, 4), new Move(blackPlayerStartPos, 15)}), rollDice, out));
//
//        Assert.assertNull(target.canTurn(PlayerColor.BLACK, new Turn(new Move[] { new Move(blackPlayerStartPos + 2, 4), new Move(blackPlayerStartPos, 2)}), rollDice, out));
//        Assert.assertNull(target.canTurn(PlayerColor.BLACK, new Turn(new Move[] { new Move(blackPlayerStartPos, 2), new Move(blackPlayerStartPos + 2, 4)}), rollDice, out));
//    }
}
