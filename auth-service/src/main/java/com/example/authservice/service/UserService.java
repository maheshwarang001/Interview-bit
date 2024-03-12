package com.example.authservice.service;

import com.example.authservice.dao.UserDao;
import com.example.authservice.entity.Date;
import com.example.authservice.entity.User;
import com.example.authservice.entity.UserCredential;
import com.example.authservice.entity.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;


@Service
public class UserService implements UserImpl{

    @Autowired
    private UserDao dao;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public Boolean createUser(UserResponse userResponse) {

        Date date = new Date();
        date.setCreatedAt(LocalDateTime.now());
        date.setExpires(LocalDateTime.now().plusYears(2));

        User user = new User();
        user.setFirstname(userResponse.getFirstname());
        user.setLastname(userResponse.getLastname());
        user.setEmail(userResponse.getEmail());
        user.setPassword(encoder.encode(userResponse.getPassword()));
        user.setDetails(date);

        return dao.saveUserDB(user);

    }

    @Override
    public User findByExist(String username) {
        if(username.isEmpty()) return null;

        return dao.userExist(username);
    }

    @Override
    public UserCredential userCredentialExists(String username) {
        if(username.isEmpty()) return null;

        User u = findByExist(username);
        return new UserCredential(u.getEmail(),u.getPassword());
    }

    @Override
    public UUID getUUID(String email) {
        if(email.isEmpty()) return null;
        return dao.getUUIDByEmail(email);
    }
}
