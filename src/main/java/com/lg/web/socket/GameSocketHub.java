package com.lg.web.socket;

import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// Warning: This class contains in-memory state - websocket connections. Take this into account, if scaling out application.
public class GameSocketHub {

    private static Map<String, Map<String, Session>> sessionsPerGame = new ConcurrentHashMap<>();

    public static void publishForGame(String gameId, GameMessage message) {
        if (!sessionsPerGame.containsKey(gameId)) {
            System.out.println(String.format("Can't publish websocket message, game %s not found", gameId));
            return;
        }

        sessionsPerGame.get(gameId).values().stream().filter(Session::isOpen).forEach(x -> {
            try {

                x.getRemote().sendString(message.toString());
                System.out.println("Published push message for session: " + x.getRemote().getInetSocketAddress().getAddress() + ", game:" + gameId + " message:" + message);
            } catch (IOException e) {
                System.out.println("Error when pushing message: " + e);
            }
        });
    }

    public static void connectPlayerToGame(Session userSession, String gameId, String playerId) {
        sessionsPerGame.putIfAbsent(gameId, new ConcurrentHashMap<>());
        sessionsPerGame.get(gameId).putIfAbsent(playerId, userSession);
    }

    public static void disconnectPlayerFromGame(String gameId, String playerId) {
        Map<String, Session> gameSessions = sessionsPerGame.getOrDefault(gameId, null);

        if (gameSessions == null) {
            System.out.println(String.format("Cant'disconnect player %s, no such game found: %s", playerId, gameId));
            return;
        }

        if(!gameSessions.containsKey(playerId)) {
            System.out.println(String.format("Cant'disconnect player %s, his session not found for this game.", playerId, gameId));
            return;
        }

        gameSessions.remove(playerId);

        if (gameSessions.size() == 0) {
            gameSessions.remove(gameId);
        }
    }
}
