package com.example.signal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@SpringBootApplication
@EnableDiscoveryClient
public class SignalApplication {

    public static void main(String[] args) {
        SpringApplication.run(SignalApplication.class, args);
    }

}
