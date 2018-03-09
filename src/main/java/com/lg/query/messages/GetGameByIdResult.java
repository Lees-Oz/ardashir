package com.lg.query.messages;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lg.command.domain.valueobjects.BoardPoint;
import com.lg.command.domain.valueobjects.Dice;
import com.lg.query.QueryResult;

import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetGameByIdResult implements QueryResult {
    private String id;
    private Dice dice;
    private BoardPoint[] boardPoints;
    private UUID whitePlayerId;
    private UUID blackPlayerId;

    public GetGameByIdResult() {
    }

    public GetGameByIdResult(String id, Dice dice, BoardPoint[] boardPoints, UUID whitePlayerId, UUID blackPlayerId) {
        this.id = id;
        this.dice = dice;
        this.boardPoints = boardPoints;
        this.whitePlayerId = whitePlayerId;
        this.blackPlayerId = blackPlayerId;
    }

    public String getId() {
        return id;
    }

    public Dice getDice() {
        return dice;
    }

    public BoardPoint[] getBoardPoints() {
        return boardPoints;
    }

    public UUID getWhitePlayerId() {
        return whitePlayerId;
    }

    public UUID getBlackPlayerId() {
        return blackPlayerId;
    }
}

//{
//        "id": "game-ecbe6cb5-0075-41fa-ad55-f9c6848a07e2",
//        "config": {
//        "checkersCount": 15,
//        "pointsCount": 24,
//        "whiteStartPosition": 0,
//        "blackStartPosition": 12,
//        "domeBorder": 6
//        },
//        "dice": {
//        "one": 2,
//        "two": 5
//        },
//        "whitePlayerId": "ff3fce27-1c4e-4db5-98e2-7dfd380c6f9c",
//        "blackPlayerId": "59da1bb6-930f-4cf7-bbd5-ab0951f1d32a",
//        "boardPoints": []
//        }