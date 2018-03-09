package com.lg.unit;

import com.lg.command.domain.valueobjects.*;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;
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

    public void test_When_asIfMoved_Should_return_correct_move_validity() {
        // Arrange
        BackgammonBoard target =  new BackgammonBoard(DefaultBackgammonConfig.get())
            .asIfMoved(PlayerColor.BLACK, new Move(12, 15))
            .asIfMoved(PlayerColor.BLACK, new Move(3, 4))
            .asIfMoved(PlayerColor.BLACK, new Move(12, 7))
            .asIfMoved(PlayerColor.BLACK, new Move(12, 13))
            .asIfMoved(PlayerColor.WHITE, new Move(0, 9));

        // Act & Assert
        Assert.assertNotNull(target);

        List<Integer> blackBusyPoints = new ArrayList<>();
        blackBusyPoints.add(1);
        blackBusyPoints.add(7);
        blackBusyPoints.add(12);
        blackBusyPoints.add(19);

        IntStream.range(0, DefaultBackgammonConfig.get().getPointsCount() - 1).forEach(x -> {
            if (blackBusyPoints.contains(x)) {
                Assert.assertNull(target.asIfMoved(PlayerColor.WHITE, new Move(0, x)));
            } else {
                Assert.assertNotNull(target.asIfMoved(PlayerColor.WHITE, new Move(0, x)));
            }
        });

        IntStream.range(0, DefaultBackgammonConfig.get().getPointsCount() - 1).forEach(x -> {
            if (blackBusyPoints.contains(x)) {
                Assert.assertNull(target.asIfMoved(PlayerColor.WHITE, new Move(0, x)));
            } else {
                Assert.assertNotNull(target.asIfMoved(PlayerColor.WHITE, new Move(0, x)));
            }
        });
    }

    public void test_default_boards_are_equal() {
        Assert.assertTrue((new BackgammonBoard(DefaultBackgammonConfig.get())).equals(new BackgammonBoard(DefaultBackgammonConfig.get())));
    }

    public void test_When_doTurn_Should_be_equivalent_to_do_moves() {
        // Arrange
        BackgammonBoard target = new BackgammonBoard(DefaultBackgammonConfig.get());

        // Act
        BackgammonBoard result1 = target.asIfTurned(PlayerColor.WHITE, new Turn(new Move[] {new Move(0, 4), new Move(4, 3)}));
        BackgammonBoard result2 = target
            .asIfMoved(PlayerColor.WHITE, new Move(0, 4))
            .asIfMoved(PlayerColor.WHITE, new Move(4, 3));

        // Assert
        Assert.assertNotNull(result1);
        Assert.assertNotNull(result2);
        Assert.assertTrue(result1.equals(result2));
    }
}
