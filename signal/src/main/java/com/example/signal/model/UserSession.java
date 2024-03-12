package com.example.signal.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.socket.WebSocketSession;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSession {

    private String user_id;
    private WebSocketSession session;

}
