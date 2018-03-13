package com.lg.unit;

import com.lg.command.domain.valueobjects.*;
import org.junit.Assert;

public class BoardSamples {
    public static BackgammonBoard beginGame() {
        BackgammonConfig config = new BackgammonConfig(15,24, 0, 12, 6);
        return new BackgammonBoard(config)
                .asIfTurned(PlayerColor.BLACK, new Turn(new Move[] {
                        new Move(12, 6),
                        new Move(18, 5)}))
                .asIfTurned(PlayerColor.BLACK, new Turn(new Move[] {
                        new Move(23, 4),
                        new Move(3, 4)}))
                .asIfTurned(PlayerColor.BLACK, new Turn(new Move[] {
                        new Move(12, 4),
                        new Move(16, 3)}))
                .asIfTurned(PlayerColor.BLACK, new Turn(new Move[] {
                        new Move(12, 5),
                        new Move(17, 3)}))
                .asIfTurned(PlayerColor.BLACK, new Turn(new Move[] {
                        new Move(20, 3),
                        new Move(23, 2)}))
                .asIfTurned(PlayerColor.WHITE, new Turn(new Move[] {
                        new Move(0, 5),
                        new Move(5, 4)}));
    }

    public static BackgammonBoard midGame() { // See notepad drawing
        BackgammonConfig config = new BackgammonConfig(15, 24, 0, 12, 6);
        return new BackgammonBoard(config)
                .asIfTurned(PlayerColor.WHITE, new Turn(new Move[] {
                        new Move(0, 3),
                        new Move(0, 5)}))
                .asIfTurned(PlayerColor.BLACK, new Turn(new Move[] {
                        new Move(12, 6),
                        new Move(12, 2)}))
                .asIfTurned(PlayerColor.WHITE, new Turn(new Move[] {
                        new Move(5, 2),
                        new Move(7, 1)}))
                .asIfTurned(PlayerColor.BLACK, new Turn(new Move[] {
                        new Move(12, 5),
                        new Move(17, 4)}))
                .asIfTurned(PlayerColor.WHITE, new Turn(new Move[] {
                        new Move(0, 3),
                        new Move(3, 5)}))
                .asIfTurned(PlayerColor.BLACK, new Turn(new Move[] {
                        new Move(21, 5),
                        new Move(2, 3)}))
                .asIfTurned(PlayerColor.WHITE, new Turn(new Move[] {
                        new Move(0, 6),
                        new Move(6, 2)}))
                .asIfTurned(PlayerColor.BLACK, new Turn(new Move[] {
                        new Move(12, 6),
                        new Move(18, 5)}))
                .asIfTurned(PlayerColor.WHITE, new Turn(new Move[] {
                        new Move(0, 6),
                        new Move(6, 5)}))
                .asIfTurned(PlayerColor.BLACK, new Turn(new Move[] {
                        new Move(23, 6),
                        new Move(5, 4)}))
                .asIfTurned(PlayerColor.WHITE, new Turn(new Move[] {
                        new Move(0, 2),
                        new Move(2, 5)}))
                .asIfTurned(PlayerColor.BLACK, new Turn(new Move[] {
                        new Move(12, 5),
                        new Move(17, 6)}))
                .asIfTurned(PlayerColor.WHITE, new Turn(new Move[] {
                        new Move(11, 2),
                        new Move(13, 3)}))
                .asIfTurned(PlayerColor.BLACK, new Turn(new Move[] {
                        new Move(23, 2),
                        new Move(1, 4)}));
    }

    public static BackgammonBoard finishedGame() {
        BackgammonConfig config = new BackgammonConfig(15, 24, 0, 12, 6);
        return new BackgammonBoard(config)
                // Bring all to dome - 5 at 21, 22, 23
                .asIfTurned(PlayerColor.WHITE, new Turn(new Move[] {
                        new Move(0, 21),
                        new Move(0, 22),
                        new Move(0, 23)
                }))
                .asIfTurned(PlayerColor.WHITE, new Turn(new Move[] {
                        new Move(0, 21),
                        new Move(0, 22),
                        new Move(0, 23)
                }))
                .asIfTurned(PlayerColor.WHITE, new Turn(new Move[] {
                        new Move(0, 21),
                        new Move(0, 22),
                        new Move(0, 23)
                }))
                .asIfTurned(PlayerColor.WHITE, new Turn(new Move[] {
                        new Move(0, 21),
                        new Move(0, 22),
                        new Move(0, 23)
                }))
                .asIfTurned(PlayerColor.WHITE, new Turn(new Move[] {
                        new Move(0, 21),
                        new Move(0, 22),
                        new Move(0, 23)
                }))
                // Do some Black turns
                .asIfTurned(PlayerColor.BLACK, new Turn(new Move[] {
                        new Move(12, 21),
                        new Move(12, 22),
                        new Move(12, 23)
                }))
                .asIfTurned(PlayerColor.BLACK, new Turn(new Move[] {
                        new Move(12, 21),
                        new Move(12, 22),
                        new Move(12, 23)
                }))
                .asIfTurned(PlayerColor.BLACK, new Turn(new Move[] {
                        new Move(12, 21),
                        new Move(12, 22),
                        new Move(12, 23)
                }))
                .asIfTurned(PlayerColor.BLACK, new Turn(new Move[] {
                        new Move(12, 21),
                        new Move(12, 22),
                        new Move(12, 23)
                }))
                .asIfTurned(PlayerColor.BLACK, new Turn(new Move[] {
                        new Move(12, 21),
                        new Move(12, 22),
                        new Move(12, 23)
                }))
                // Remove all for White
                .asIfTurned(PlayerColor.WHITE, new Turn(new Move[] {
                        new Move(21, 3),
                        new Move(22, 2),
                        new Move(23, 1)
                }))
                .asIfTurned(PlayerColor.WHITE, new Turn(new Move[] {
                        new Move(21, 3),
                        new Move(22, 2),
                        new Move(23, 1)
                }))
                .asIfTurned(PlayerColor.WHITE, new Turn(new Move[] {
                        new Move(21, 3),
                        new Move(22, 2),
                        new Move(23, 1)
                }))
                .asIfTurned(PlayerColor.WHITE, new Turn(new Move[] {
                        new Move(21, 3),
                        new Move(22, 2),
                        new Move(23, 1)
                }))
                .asIfTurned(PlayerColor.WHITE, new Turn(new Move[] {
                        new Move(21, 3),
                        new Move(22, 2),
                        new Move(23, 1)
                }));

    }
}
