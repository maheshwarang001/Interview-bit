package com.example.email_service.consumer;


import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

    @KafkaListener(topics = "email", groupId = "link-service")
    public void listen(String message){
        System.out.println(message);
    }
}
