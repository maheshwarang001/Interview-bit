package com.example.roomregisterservice.service;

import com.example.roomregisterservice.dao.RoomDao;
import com.example.roomregisterservice.message.MessageProducer;
import com.example.roomregisterservice.model.*;
import com.example.roomregisterservice.rest.CheckerService;
import lombok.extern.slf4j.Slf4j;
import netscape.javascript.JSObject;
import org.bson.json.JsonObject;
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


    @Autowired
    private MessageProducer messageProducer;



    public List<Room> getall(){
        return roomDao.getAll();
    }



    public boolean createRoom(Room room_data, UUID rId){



        if(service.checkRecruiterAndJobId(room_data.getJobId(), rId)){


            UUID room_ID = UUID.randomUUID();


            room_data.setRoomID(room_ID);

            LocalDateTime time = LocalDateTime.now().plusMinutes(15);
            room_data.setSchedule(time);

            for (Map.Entry<Role, UserDetails> mp : room_data.getUserList().entrySet()) {
                UUID user_ID = UUID.randomUUID();
                UserDetails u = mp.getValue();
                u.setUserId(user_ID);

                String user = String.format("%s/%s/%s", room_ID, user_ID, mp.getKey());

                Map<String,String> map = new HashMap<>();
                map.put("email", u.getEmail());
                map.put("link",user);
                map.put("time",time.toString());

                System.out.println(map.toString());


                messageProducer.sendMessage("email", map.toString());
            }


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
