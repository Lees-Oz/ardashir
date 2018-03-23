package com.lg.web;

import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GameSocketHub {

    static Map<String, Map<String, Session>> sessionsPerGame = new ConcurrentHashMap<>();

    public static void publishForGame(GameMessage event) {
        if (!sessionsPerGame.containsKey(event.getGameId())) {
            System.out.println(String.format("Can't publish websocket message, game %s not found", event.getGameId()));
            return;
        }

        sessionsPerGame.get(event.getGameId()).values().stream().filter(Session::isOpen).forEach(x -> {
            try {
                x.getRemote().sendString(event.getAction());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static void connectUser(Session userSession, String gameId, String playerId) {
        sessionsPerGame.putIfAbsent(gameId, new ConcurrentHashMap<>());
        sessionsPerGame.get(gameId).putIfAbsent(playerId, userSession);
    }

    public static void disconnectUser(String gameId, String playerId) {
        Map<String, Session> gameSessions = sessionsPerGame.getOrDefault(gameId, null);

        if (gameSessions == null) {
            return;
        }

        gameSessions.remove(playerId);

        if (gameSessions.size() == 0) {
            gameSessions.remove(gameId);
        }
    }
}
