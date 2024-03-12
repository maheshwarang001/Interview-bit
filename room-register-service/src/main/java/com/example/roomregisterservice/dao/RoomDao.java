package com.example.roomregisterservice.dao;

import com.example.roomregisterservice.model.Role;
import com.example.roomregisterservice.model.Room;
import com.example.roomregisterservice.repository.RoomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class RoomDao {

    @Autowired
    RoomRepo repo;


    public void createRoom(Room room){
        repo.save(room);
    }

    public List<Room> getAll(){
        return repo.findAll();
    }

    public Room getRoom(UUID id){
        return repo.findByRoomID(id);
    }




}
