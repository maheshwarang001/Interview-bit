package com.example.roomregisterservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan("com.example.roomregisterservice.*")
@EnableDiscoveryClient
public class RoomRegisterServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RoomRegisterServiceApplication.class, args);
    }

}
