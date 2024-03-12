package com.example.authservice.dao;


import com.example.authservice.entity.User;
import com.example.authservice.repository.UserDbRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class UserDao {

    @Autowired
    private UserDbRepository userDbRepository;

    public boolean saveUserDB(User user){

        try{

            userDbRepository.save(user);

            return true;

        }catch (Exception ex){
             log.warn(ex.getMessage());
             ex.printStackTrace();
        }

        return false;

    }




    public User userExist(String email){

        return userDbRepository.findByEmail(email);

    }


    public UUID getUUIDByEmail(String username) {
        User u = userExist(username);
        return u.getUuid();
    }


}
