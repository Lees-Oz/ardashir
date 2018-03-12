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

    public void test_When_asIfTurned_Should_return_correct_turn_validity1() {
        // Arrange
        BackgammonConfig config = new BackgammonConfig(15,24, 0, 12, 6);
        BackgammonBoard target =  new BackgammonBoard(config)
            .asIfTurned(PlayerColor.BLACK, new Turn(new Move[] {new Move(12, 6), new Move(18, 5)}))
            .asIfTurned(PlayerColor.BLACK, new Turn(new Move[] {new Move(23, 4), new Move(3, 4)}))
            .asIfTurned(PlayerColor.BLACK, new Turn(new Move[] {new Move(12, 4), new Move(16, 3)}))
            .asIfTurned(PlayerColor.BLACK, new Turn(new Move[] {new Move(12, 5), new Move(17, 3)}))
            .asIfTurned(PlayerColor.BLACK, new Turn(new Move[] {new Move(20, 3), new Move(23, 2)}))
            .asIfTurned(PlayerColor.WHITE, new Turn(new Move[] {new Move(0, 5), new Move(5, 4)}));

        // Act & Assert
        Assert.assertNotNull(target);

        List<Integer> blackBusyPoints = new ArrayList<>();
        blackBusyPoints.add(1);
        blackBusyPoints.add(7);
        blackBusyPoints.add(12);
        blackBusyPoints.add(19);

        IntStream.range(0, config.getPointsCount() - 1).forEach(x -> {
            if (blackBusyPoints.contains(x)) {
                Assert.assertNull(target.asIfTurned(PlayerColor.WHITE, new Turn(new Move[] {new Move(0, 2), new Move(0, x)})));
            } else {
                Assert.assertNotNull(target.asIfTurned(PlayerColor.WHITE, new Turn(new Move[] {new Move(0, 2), new Move(0, x)})));
            }
        });
    }

    public void test_When_asIfTurned_Should_return_correct_turn_validity2() {
        // Arrange
        BackgammonConfig config = new BackgammonConfig(15, 24, 0, 12, 6);

        BackgammonBoard target =  new BackgammonBoard(config)
                .asIfTurned(PlayerColor.WHITE, new Turn(new Move[] {new Move(0, 3), new Move(0, 5)}))
                .asIfTurned(PlayerColor.BLACK, new Turn(new Move[] {new Move(12, 6), new Move(12, 2)}))
                .asIfTurned(PlayerColor.WHITE, new Turn(new Move[] {new Move(5, 2), new Move(7, 1)}))
                .asIfTurned(PlayerColor.BLACK, new Turn(new Move[] {new Move(12, 5), new Move(17, 4)}))
                .asIfTurned(PlayerColor.WHITE, new Turn(new Move[] {new Move(0, 3), new Move(3, 5)}))
                .asIfTurned(PlayerColor.BLACK, new Turn(new Move[] {new Move(21, 5), new Move(2, 3)}))
                .asIfTurned(PlayerColor.WHITE, new Turn(new Move[] {new Move(0, 6), new Move(6, 2)}))
                .asIfTurned(PlayerColor.BLACK, new Turn(new Move[] {new Move(12, 6), new Move(18, 5)}))
                .asIfTurned(PlayerColor.WHITE, new Turn(new Move[] {new Move(0, 6), new Move(6, 5)}))
                .asIfTurned(PlayerColor.BLACK, new Turn(new Move[] {new Move(23, 6), new Move(5, 4)}))
                .asIfTurned(PlayerColor.WHITE, new Turn(new Move[] {new Move(0, 2), new Move(2, 5)}))
                .asIfTurned(PlayerColor.BLACK, new Turn(new Move[] {new Move(12, 5), new Move(17, 6)}))
                .asIfTurned(PlayerColor.WHITE, new Turn(new Move[] {new Move(11, 2), new Move(13, 3)}))
                .asIfTurned(PlayerColor.BLACK, new Turn(new Move[] {new Move(23, 2), new Move(1, 4)}));

        // Act & Assert
        Assert.assertNull(target.asIfTurned(PlayerColor.BLACK, new Turn(new Move[] {new Move(12, 4), new Move(16,2 )})));
        Assert.assertNotNull(target.asIfTurned(PlayerColor.BLACK, new Turn(new Move[] {new Move(12, 2), new Move(14,4 )})));

        Assert.assertNull(target.asIfTurned(PlayerColor.BLACK, new Turn(new Move[] {new Move(5, 1), new Move(9,3 )})));
        Assert.assertNotNull(target.asIfTurned(PlayerColor.BLACK, new Turn(new Move[] {new Move(5, 1), new Move(9,2 )})));

        Assert.assertNotNull(target);
    }


    //public void test_When_tryTurn_Should_return_correct_turn_validity

    public void test_default_boards_are_equal() {
        Assert.assertEquals((new BackgammonBoard(new BackgammonConfig(15, 24, 0, 12, 6))),
                (new BackgammonBoard(new BackgammonConfig(15, 24, 0, 12, 6))));
    }
}
