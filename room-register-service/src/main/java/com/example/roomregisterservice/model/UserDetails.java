package com.example.roomregisterservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;

import java.util.UUID;

@Data
@AllArgsConstructor
public class UserDetails {



    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID userId;

    @NotNull
    private String Email;

    @NotNull
    private String firstname;

    @NotNull
    private String lastname;




}
