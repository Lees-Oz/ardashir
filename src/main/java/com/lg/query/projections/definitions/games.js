fromCategory("game")
    .foreachStream()
    .when({
        "$init": function() {
            return {}
        },
        "GameStarted": function(s, e) {
            var body = JSON.parse(e.bodyRaw);

            s.id = body.gameId,
            s.config = body.config,
            s.dice = body.dice,
            s.whitePlayerId = body.whitePlayerId,
            s.blackPlayerId = body.blackPlayerId,
            s.boardPoints = []
        },
        "PlayerTurned": function(s, e) {
            var body = JSON.parse(e.bodyRaw);

            s.dice = body.dice;
            s.boardPoints = body.boardPoints;
        }
    })