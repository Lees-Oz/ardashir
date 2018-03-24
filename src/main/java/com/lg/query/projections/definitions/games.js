fromCategory("game")
    .foreachStream()
    .when({
        "$init": function() {
            return {}
        },
        "NewGameSessionStarted": function(s, e) {
            var body = JSON.parse(e.bodyRaw);

            s.id = body.gameId;
            s.whitePlayerId = body.byPlayerId;
            s.status = "waitingPartner"
        },
        "GameStarted": function(s, e) {
            var body = JSON.parse(e.bodyRaw);

            s.config = body.config;
            s.dice = body.dice;
            s.blackPlayerId = body.blackPlayerId;
            s.boardPoints = body.boardPoints;
            s.status = "started";
        },
        "PlayerTurned": function(s, e) {
            var body = JSON.parse(e.bodyRaw);

            s.dice = body.dice;
            s.boardPoints = body.boardPoints;
        }
    })