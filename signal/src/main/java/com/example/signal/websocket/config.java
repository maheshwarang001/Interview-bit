package com.example.signal.websocket;

import com.example.signal.redis.publisher.Publisher;
import com.example.signal.redis.subscriber.Subscriber;
import com.example.signal.rest.RestConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.util.Map;

@Configuration
@EnableWebSocket
@Slf4j
public class config implements WebSocketConfigurer {


    @Autowired
    RestConfig restConfig;

    @Autowired
    Publisher publisher;

    @Autowired
    Subscriber subscriber;

    @Autowired
    WebSocketSessionManager webSocketSessionManager;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new WebSocketHandler
                                (this.publisher, this.subscriber, this.webSocketSessionManager),
                        "/meeting/room/{roomID}/{userID}")
                .addInterceptors(getParametersInterceptors())
                //.setAllowedOriginPatterns("http://localhost:3000")
                .setAllowedOriginPatterns("*");
                //.withSockJS();
    }




    @Bean
    public HandshakeInterceptor getParametersInterceptors() {
        return new HandshakeInterceptor() {
            @Override
            public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, org.springframework.web.socket.WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
                String path = request.getURI().getPath();
                String roomId = Helper.getRoomIdFromUrl(path);
                String userId = Helper.getUserIdFromUrl(path);


                Boolean ans = false;
                try {
                    String url = "http://localhost:8082/third-party/v1/res/valid?roomID=" + roomId + "&userID=" + userId;
                    ans = restConfig.restTemplate().getForObject(url, Boolean.class);
                    System.out.println(ans);


                } catch (Exception ex) {


                    log.error(ex.getMessage());

                }

                log.info(ans +" ");

                if (ans == false) {
                    log.info("The Room service Entered False class");
                    return false;
                }

                log.info("Extracted Room ID: {}, User ID: {}", roomId, userId);
                attributes.put("roomID", roomId);
                attributes.put("userID", userId);

                log.info(attributes.get("roomID").toString());
                return true;

            }


            @Override
            public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, org.springframework.web.socket.WebSocketHandler wsHandler, Exception exception) {

            }
        };
    }
}
