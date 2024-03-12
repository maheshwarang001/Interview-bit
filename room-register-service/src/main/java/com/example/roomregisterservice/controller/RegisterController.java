package com.example.roomregisterservice.controller;


import com.example.roomregisterservice.model.Role;
import com.example.roomregisterservice.model.Room;
import com.example.roomregisterservice.model.UserDetails;
import com.example.roomregisterservice.service.RoomService;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RequestMapping("/third-party/v1")
@RestController
public class RegisterController {

    private final ExecutorService threadPool = Executors.newFixedThreadPool(20);

    @Autowired
    private RoomService service;


    @GetMapping("/res/get")
    public List<Room> getAll(){
        return service.getall();
    }


    @GetMapping("/res/data")
    public Map<String, String> r() {

        return Map.of("res", "ok");
    }


    @PostMapping("/res/create-room")
    public ResponseEntity<Map<String, String>> createRoom(
            @RequestBody Room registerForm,
            @RequestHeader("X-User-ID") UUID rID
    ) {
        if (rID == null) throw new ValidationException();

        boolean ans = service.createRoom(registerForm, rID);


        if (ans) {
            return ResponseEntity.ok().body(Map.of("response", "success"));
        } else {
            return ResponseEntity.badRequest().body(Map.of("response", "fail"));
        }

    }


    @GetMapping("/res/getUsers")
    public ResponseEntity<Map<Role, UserDetails>> getMeetinDetails(@RequestParam("roomID") UUID roomID) {

        Map<Role, UserDetails> mp = service.getRoomUserDetails(roomID);

        if (mp == null) {
            return ResponseEntity.badRequest().body(null);
        }

        return ResponseEntity.ok().body(mp);

    }


    @GetMapping("/res/valid")
    public boolean checkRoomIDAndUserID(
            @RequestParam("roomID") String roomID,
            @RequestParam("userID") String userID
    ){

        System.out.println(roomID);
        System.out.println(userID);

        boolean ans =  service.checkRoomIDAndUserID(UUID.fromString(roomID),UUID.fromString(userID));
        System.out.println(ans);
        return ans;

    }

}
