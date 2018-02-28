package com.lg.command.domain.valueobjects;


import com.lg.command.domain.events.PlayerTurned;
import com.lg.command.domain.services.RollDice;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BackgammonGame {
    private BackgammonBoard board;
    private PlayerColor nextPlayerColor;
    private Dice dice;
    private ProvideBackgammonConfig config;

    public BackgammonGame(ProvideBackgammonConfig config, Dice dice) {
        this.config = config;
        this.dice = dice;
        this.board = new BackgammonBoard(config);
    }

    public PlayerTurned canTurn(PlayerColor playerColor, Turn turn, RollDice rollDice, StringBuilder out) {
        if (nextPlayerColor != playerColor && nextPlayerColor != null) {
            out.append("It's another player's turn expected.");
            return null;
        }

        List<BackgammonBoard> possibleBoards = this.getPossibleBoardsForSteps(playerColor, getStepsByDice(dice));

        BackgammonBoard resultBoard = board.asIfTurned(playerColor, turn);

        if (possibleBoards.contains(resultBoard)) {
            return new PlayerTurned(playerColor, turn, rollDice.roll(), resultBoard.getPoints());
        }

        out.append("Can't do that turn.");
        return null;
    }

    public void turn(PlayerColor playerColor, Turn turn) {
        board = board.asIfTurned(playerColor, turn);
        nextPlayerColor = getOppositePlayerColor(playerColor);
    }

    private PlayerColor getOppositePlayerColor(PlayerColor color) {
        return color == PlayerColor.WHITE ? PlayerColor.BLACK : PlayerColor.WHITE;
    }

    private List<BackgammonBoard> getPossibleBoardsForSteps(PlayerColor playerColor, int[] steps) {
        List<TurnCandidate> candidates = new ArrayList<>();
        candidates.add(new TurnCandidate(new Turn(new Move[] {}), board));
        TurnCandidate emptyTurnCandidate = new TurnCandidate(new Turn(new Move[] {}), board);

        if (steps.length == 2) {

            for (int i = 0; i < config.getPointsCount(); i++) {
                Move move1 = new Move(i, steps[0]);
                if (board.canMove(playerColor, move1)) {
                    BackgammonBoard newBoard1 = board.asIfMoved(playerColor, move1);
                    candidates.add(new TurnCandidate(new Turn(new Move[] { move1 }), newBoard1));

                    for (int j = 0; j < config.getPointsCount(); j++) {
                        Move move2 = new Move(j, steps[1]);

                        if (newBoard1.canMove(playerColor, move2)) {
                            BackgammonBoard newBoard2 = newBoard1.asIfMoved(playerColor, move2);
                            candidates.add(new TurnCandidate(new Turn(new Move[] { move1, move2 }), newBoard2));
                        }
                    }
                }

            }
        } else if(steps.length == 4) {
            // calculate here for double dice
        }

        TurnCandidate longestCandidate = candidates.stream()
                .max(Comparator.comparingInt(o -> o.getTurn().getTotalSteps()))
                .orElse(emptyTurnCandidate);

        int longest = longestCandidate.getTurn().getTotalSteps();

        return candidates.stream()
                .filter(x -> x.getTurn().getTotalSteps() == longest)
                .map(TurnCandidate::getBoard)
                .distinct()
                .collect(Collectors.toList());
    }

    private int[] getStepsByDice(Dice dice) {
        if (dice.getOne() == dice.getTwo()) { // same numbers on dice double the steps
            return new int[] {dice.getOne(), dice.getOne(), dice.getOne(), dice.getOne()};
        }

        return new int[] {dice.getOne(), dice.getTwo()};
    }
}
