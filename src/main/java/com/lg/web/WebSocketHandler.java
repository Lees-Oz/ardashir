package com.lg.web;

import com.google.inject.Inject;
import com.lg.utils.SerializeJson;
import org.eclipse.jetty.websocket.api.*;
import org.eclipse.jetty.websocket.api.annotations.*;

import java.io.IOException;

@WebSocket
public class WebSocketHandler implements HandleWebsocket {
    private SerializeJson serializer;

    @Inject
    public WebSocketHandler(SerializeJson serializer) {
        this.serializer = serializer;
    }

    @OnWebSocketConnect
    public void onConnect(Session user) throws Exception {
        String gameId = user.getUpgradeRequest().getParameterMap().get("gameId").get(0);
        String playerId = user.getUpgradeRequest().getParameterMap().get("playerId").get(0);


        if (gameId.isEmpty() || playerId.isEmpty()) {
            System.out.println("onConnect - gameId and/or playerId is not provided, doing nothing");
            return;
        }

        GameSocketHub.connectUser(user, gameId, playerId);

        System.out.println("onConnect - gameId: " + gameId + ", playerId: " + playerId);
    }

    @OnWebSocketClose
    public void onClose(Session user, int statusCode, String reason) {
        String gameId = user.getUpgradeRequest().getParameterMap().get("gameId").get(0);
        String playerId = user.getUpgradeRequest().getParameterMap().get("playerId").get(0);

        if (gameId.isEmpty() || playerId.isEmpty()) {
            System.out.println("onClose - gameId and/or playerId is not provided, doing nothing");
            return;
        }

        System.out.println("onClose - gameId: " + gameId + ", playerId: " + playerId);

        GameSocketHub.disconnectUser(gameId, playerId);
    }

    @OnWebSocketMessage
    public void onMessage(Session user, String message) throws IOException {
        String gameId = user.getUpgradeRequest().getParameterMap().get("gameId").get(0);
        String playerId = user.getUpgradeRequest().getParameterMap().get("playerId").get(0);

        if (gameId.isEmpty() || playerId.isEmpty()) {
            System.out.println("onMessage - gameId and/or playerId is not provided, doing nothing");
            return;
        }

        System.out.println("onMessage - gameId: " + gameId + ", playerId: " + playerId + ", message: " + message);

        GameMessage event = (GameMessage)serializer.deserialize(message, GameMessage.class);
        GameSocketHub.publishForGame(event);
    }
}