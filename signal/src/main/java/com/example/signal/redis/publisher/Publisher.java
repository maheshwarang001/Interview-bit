package com.example.signal.redis.publisher;


import io.lettuce.core.RedisClient;
import org.springframework.stereotype.Component;

@Component
public class Publisher {

    RedisClient redisClient;

    public Publisher(){
        this.redisClient = RedisClient.create("redis://localhost:6379");
    }

    public void publish(String roomID, String message){
        var connection  = this.redisClient.connect();
        connection.sync().publish(roomID,message);
    }

}
