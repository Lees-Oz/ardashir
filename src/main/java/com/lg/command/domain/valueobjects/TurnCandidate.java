package com.lg.command.domain.valueobjects;

public class TurnCandidate {
    private Turn turn;
    private BackgammonBoard board;


    TurnCandidate(Turn turn, BackgammonBoard board) {
        this.turn = turn;
        this.board = board;
    }

    public Turn getTurn() {
        return turn;
    }

    public BackgammonBoard getBoard() {
        return board;
    }
}
