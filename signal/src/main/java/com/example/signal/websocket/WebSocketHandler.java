package com.example.signal.websocket;

import com.example.signal.redis.publisher.Publisher;
import com.example.signal.redis.subscriber.Subscriber;
import com.example.signal.rest.RestConfig;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.naming.AuthenticationException;
import java.util.Arrays;

@Component
@Slf4j
public class WebSocketHandler extends TextWebSocketHandler {

    private final Publisher publisher;
    private final Subscriber subscriber;


    @Autowired
    private RestConfig config;

    private final WebSocketSessionManager webSocketSessionManager;

    @Autowired
    public WebSocketHandler(Publisher publisher, Subscriber subscriber, WebSocketSessionManager webSocketSessionManager) {
        this.publisher = publisher;
        this.subscriber = subscriber;
        this.webSocketSessionManager = webSocketSessionManager;
    }


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        //this.webSocketSessionManager.addWebSocketSession(session);

        String roomID = (String) session.getAttributes().get("roomID");
        String userID = (String) session.getAttributes().get("userID");





            this.webSocketSessionManager.addWebSocketSession(session,roomID,userID);
            //subscribe
            this.subscriber.subscribe(roomID);

            return;



    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String roomID = (String) session.getAttributes().get("roomID");
        this.webSocketSessionManager.removeSocketSession(session,roomID);

        //unsubscribe
        this.subscriber.unsubscribe(roomID);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        try {
            String payload = message.getPayload();

            // Assuming payload is a JSON string, parse it into a JSONObject

            // Convert the JSON object back to a string


            String userID = (String) session.getAttributes().get("userID");
            String roomID = (String) session.getAttributes().get("roomID");


           // String [] b = {payload,userID};

            this.publisher.publish(roomID, payload + "!" + userID);
        } catch (JSONException e) {
            // Handle JSON parsing error
            e.printStackTrace();
        }
    }



}
