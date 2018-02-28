fromAll()
.when({
    $init: function() {
        return {}
    },
    NewGameSessionStarted: function(s, e) {
        var body = JSON.parse(e.bodyRaw);
        s[body.byPlayerId] = body.gameId;
    },
    PartnerJoinedGameSession: function(s, e) {
        var body = JSON.parse(e.bodyRaw);
        s[body.playerId] = body.gameId;
    }
})