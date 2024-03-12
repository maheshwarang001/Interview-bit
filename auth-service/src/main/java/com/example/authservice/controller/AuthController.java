package com.example.authservice.controller;


import com.example.authservice.config.jwt.JwtService;
import com.example.authservice.entity.UserCredential;
import com.example.authservice.entity.UserResponse;
import com.example.authservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/auth/v1")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    JwtService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;


    @GetMapping("/res/data")
    public String r(@RequestHeader("X-User-ID") String rID){

        return rID;
    }


    @PostMapping("/res/create-recruiter")
    public ResponseEntity<Map<String, Boolean>> createUser(@RequestBody UserResponse userResponse){

        Boolean response = userService.createUser(userResponse);

        if(response == true){
            return  ResponseEntity.ok().body(Map.of("response",response));
        }
        else{
            return ResponseEntity.badRequest().body(Map.of("response",response));
        }
    }

    @PostMapping("/res/token")
    public ResponseEntity<Map<String, String>> getToken(@RequestBody UserCredential userCredential) throws Exception {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userCredential.getEmail(), userCredential.getPwd())
        );

        if (authentication.isAuthenticated()) {

            UUID getId = userService.getUUID(userCredential.getEmail());
            try {
                String token = jwtService.generateToken(String.valueOf(getId), userCredential.getEmail());
                return ResponseEntity.ok(Map.of("token", token));
            }
            catch (Exception e){
                return ResponseEntity.internalServerError().body(Map.of("token", "Failed ") );
            }

        }

        return ResponseEntity.badRequest().body(Map.of("token", "User not found "));

    }






}
