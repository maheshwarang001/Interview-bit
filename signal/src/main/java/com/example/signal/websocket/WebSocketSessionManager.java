package com.example.signal.websocket;

import com.example.signal.model.UserSession;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;
import lombok.extern.slf4j.Slf4j;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
@Slf4j
public class WebSocketSessionManager {

    private final Map<String, List<String>> map = new HashMap<>(); // room id and list of user ids
    private final Map<String, WebSocketSession> sessionMap = new HashMap<>(); // users ids and their socketsessions

    public void addWebSocketSession(WebSocketSession webSocketSession, String roomID, String userID) {

        map.computeIfAbsent(roomID, k -> new CopyOnWriteArrayList<>()).add(userID);
        sessionMap.put(userID, webSocketSession);
    }

    public void removeSocketSession(WebSocketSession webSocketSession, String roomID) {
        // Get the user ID associated with the WebSocket session
        String userID = null;
        for (Map.Entry<String, WebSocketSession> entry : sessionMap.entrySet()) {
            if (entry.getValue().equals(webSocketSession)) {
                userID = entry.getKey();
                break;
            }
        }

        // Remove the user from the session map
        if (userID != null) {
            sessionMap.remove(userID);
        }

        // Remove the user from the room map
        List<String> usersInRoom = map.get(roomID);
        if (usersInRoom != null) {
            usersInRoom.remove(userID);
            // If there are no more users in the room, remove the room from the map
            if (usersInRoom.isEmpty()) {
                map.remove(roomID);
            }
        }
    }


    public List<WebSocketSession> getSessions(String roomID,String senderID) {

        List<WebSocketSession> sessions = new ArrayList<>();
        List<String> userIds = map.get(roomID);
        if (userIds != null) {
            for (String userId : userIds) {

                if(userId.equals(senderID)) continue;

                WebSocketSession session = sessionMap.get(userId);
                if (session != null) {
                    sessions.add(session);
                }
            }
        }
        return sessions;
    }


}
