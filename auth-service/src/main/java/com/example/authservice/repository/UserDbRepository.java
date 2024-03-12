package com.example.authservice.repository;

import com.example.authservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserDbRepository extends JpaRepository<User, UUID> {

    User findByEmail(String email);

   // UUID get
}
