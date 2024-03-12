package com.example.roomregisterservice.service;

import com.example.roomregisterservice.dao.RoomDao;
import com.example.roomregisterservice.model.*;
import com.example.roomregisterservice.rest.CheckerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class RoomService {

    @Autowired
    RoomDao roomDao;

    @Autowired
    CheckerService service;



    public List<Room> getall(){
        return roomDao.getAll();
    }



    public boolean createRoom(Room room_data, UUID rId){



        if(service.checkRecruiterAndJobId(room_data.getJobId(), rId)){

            room_data.setRoomID(UUID.randomUUID());

            for(Map.Entry<Role,UserDetails> mp : room_data.getUserList().entrySet()){
                UserDetails u = mp.getValue();
                u.setUserId(UUID.randomUUID());
            }

            room_data.setSchedule(LocalDateTime.now().plusDays(1));
            roomDao.createRoom(room_data);
            return true;

        }
        else{
            return false;
        }

    }


    public Map<Role,UserDetails> getRoomUserDetails(UUID id){

        Room room = roomDao.getRoom(id);

        if(room != null){

            return room.getUserList();

        }

        return null;
    }


    public boolean checkRoomIDAndUserID(UUID roomID, UUID userID) {
        Room room = roomDao.getRoom(roomID);

        if (room != null) {
            // Check if the user is associated with the room
            for (UserDetails userDetails : room.getUserList().values()) {
                if (userDetails.getUserId().equals(userID)) {
                    return true; // User found in userList
                }
            }
        }
        return false; // User not found in userList or room does not exist
    }




}
