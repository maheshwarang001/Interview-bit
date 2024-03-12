package com.example.roomregisterservice.repository;

import com.example.roomregisterservice.dao.RoomDao;
import com.example.roomregisterservice.model.Room;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface RoomRepo extends MongoRepository<Room,UUID>{
    Room findByRoomID(UUID ID);


    boolean existsByRoomID(UUID ID);




}
