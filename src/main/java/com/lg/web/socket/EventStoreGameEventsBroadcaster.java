package com.lg.web.socket;

import com.github.msemys.esjc.*;
import com.google.inject.Inject;
import com.lg.utils.SerializeJson;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import static rx.Observable.create;

public class EventStoreGameEventsBroadcaster implements BroadcastGameEvents {

    private EventStore es;
    private SerializeJson serializer;

    private CompletableFuture<Subscription> eventstoreStoreSubscription;

    @Inject
    public EventStoreGameEventsBroadcaster(EventStore es, SerializeJson serializer) {
        this.es = es;
        this.serializer = serializer;
    }


    private void subscribe() {
        eventstoreStoreSubscription = es.subscribeToStream("$ce-game", false,
                new VolatileSubscriptionListener() {
                    @Override
                    public void onEvent(Subscription subscription, ResolvedEvent event) {
                        try {
                            SubscriptionEvent e = (SubscriptionEvent) serializer.deserialize(new String(event.event.metadata), SubscriptionEvent.class);
                            String gameId = e.get$o();
                            System.out.println("New game event. GameId:" + gameId);
                            GameSocketHub.publishForGame(gameId, new GameMessage(gameId, "unknown", "unknown"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onClose(Subscription subscription, SubscriptionDropReason reason, Exception exception) {
                        System.out.println("Subscription closed: " + reason);
                        subscribe();
                    }
                });
    }

    @Override
    public void listenGames() {
        try {
            subscribe();
        } catch(Exception e) {
            System.out.println("Failed: " + e);
        }
    }

//    public static <T> Observable<T> toObservable(CompletableFuture<T> future) {
//        return Observable . create<T>(subscriber ->
//                future.whenComplete((result, error) -> {
//                    if (error != null) {
//                        subscriber.(error);
//                    } else {
//                        subscriber.onNext(result);
//                        subscriber.onCompleted();
//                    }
//                }));
//    }
}
