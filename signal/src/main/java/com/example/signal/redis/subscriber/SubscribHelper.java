package com.example.signal.redis.subscriber;

import com.example.signal.model.UserSession;
import com.example.signal.websocket.WebSocketSessionManager;
import io.lettuce.core.pubsub.RedisPubSubListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
public class SubscribHelper implements RedisPubSubListener<String, String> {

    private WebSocketSessionManager sessionManager;


    SubscribHelper(WebSocketSessionManager sessionManager){
        this.sessionManager = sessionManager;
    }

    @Override
    public void message(String channel, String mPayLoad) {
        System.out.println(mPayLoad);

        String[] str = mPayLoad.split("!" );

        log.info(str.toString());

        String jsonString = str[0];
        String senderID = str[1];

        if (str.length != 2) {
            throw new IllegalArgumentException("Invalid message payload format");
        }

        // Send the JSON object to WebSocket sessions
        List<WebSocketSession> ws = this.sessionManager.getSessions(channel, senderID);

        for (WebSocketSession session : ws) {
            try {
                session.sendMessage(new TextMessage(jsonString));
            } catch (IOException ex) {
                log.warn("Error sending message to session: " + ex.getMessage());
            }
        }
    }

    @Override
    public void message(String s, String k1, String k3) {

    }

    @Override
    public void subscribed(String s, long l) {

    }

    @Override
    public void psubscribed(String s, long l) {

    }

    @Override
    public void unsubscribed(String s, long l) {

    }

    @Override
    public void punsubscribed(String s, long l) {

    }
}
