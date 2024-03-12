package com.example.eurekainterviewbit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaInterviewbitApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaInterviewbitApplication.class, args);
    }

}
