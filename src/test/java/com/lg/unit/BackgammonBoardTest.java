package com.lg.unit;

import com.lg.command.domain.valueobjects.*;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

public class BackgammonBoardTest extends TestCase {

    public BackgammonBoardTest(String testName )
    {
        super( testName );
    }

    public static Test suite()
    {
        return new TestSuite( BackgammonBoardTest.class );
    }

    public void test_When_asIfTurned_Should_return_correct_turn_validity1() {
        // Arrange
        BackgammonBoard target = BoardSamples.beginGame();

        // Act & Assert
        IntStream.range(0, target.getConfig().getPointsCount() - 1).forEach(x -> {

            BackgammonBoard result = target.asIfTurned(PlayerColor.WHITE, new Turn(new Move[] {
                    new Move(0, x),
                    new Move(0, 2)
                    }));

            ArrayList<Integer> occupiedPoints = new ArrayList<>(Arrays.asList(1, 7, 12, 19));

            Assert.assertEquals(occupiedPoints.contains(x), result == null);
        });

        Assert.assertNull(target.getWinner());
    }

    public void test_When_asIfTurned_Should_return_correct_turn_validity2() {
        // Arrange
        BackgammonBoard target =  BoardSamples.midGame();

        // Act & Assert
        Assert.assertNull(target.asIfTurned(PlayerColor.BLACK, new Turn(new Move[] {
                new Move(12, 4),
                new Move(16,2 )})));
        Assert.assertNotNull(target.asIfTurned(PlayerColor.BLACK, new Turn(new Move[] {
                new Move(12, 2),
                new Move(14,4 )})));

        Assert.assertNull(target.asIfTurned(PlayerColor.BLACK, new Turn(new Move[] {
                new Move(5, 1),
                new Move(9,3 )})));
        Assert.assertNotNull(target.asIfTurned(PlayerColor.BLACK, new Turn(new Move[] {
                new Move(5, 1),
                new Move(9,2 )})));

        Assert.assertNull(target.getWinner());
        // More cases here
    }

    public void test_When_all_checkers_removed_Should_be_winner() {
        Assert.assertNull(BoardSamples.beginGame().getWinner());
        Assert.assertNull(BoardSamples.midGame().getWinner());
        Assert.assertEquals(BoardSamples.finishedGame().getWinner(), PlayerColor.WHITE);
    }

    public void test_default_boards_are_equal() {
        Assert.assertEquals(
                (new BackgammonBoard(new BackgammonConfig(15, 24, 0, 12, 6))),
                (new BackgammonBoard(new BackgammonConfig(15, 24, 0, 12, 6))));

        Assert.assertNotEquals(
                (new BackgammonBoard(new BackgammonConfig(15, 24, 0, 12, 6))),
                (new BackgammonBoard(new BackgammonConfig(14, 24, 0, 12, 6))));
    }
}
