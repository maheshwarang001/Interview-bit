package com.example.authservice.service;

import com.example.authservice.entity.User;
import com.example.authservice.entity.UserCredential;
import com.example.authservice.entity.UserResponse;

import java.util.UUID;

public interface UserImpl {

    Boolean createUser(UserResponse user);

    User findByExist(String username);

     UserCredential userCredentialExists(String username);

     UUID getUUID(String email);
}
