package com.example.jobcreator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class JobCreatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(JobCreatorApplication.class, args);
    }

}
