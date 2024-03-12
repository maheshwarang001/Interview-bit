package com.example.authservice.entity;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserResponse {
    private String firstname;


    private String lastname;


    private String email;


    private String password;
}
