fromAll()
.when({
    $init: function() {
        return {}
    },
    NewGameRegistered: function(s, e) {
        var body = JSON.parse(e.bodyRaw);
        s[body.playerId] = body.gameId;
    },
    PartnerJoined: function(s, e) {
        var body = JSON.parse(e.bodyRaw);
        s[body.playerId] = body.gameId;
    }
})