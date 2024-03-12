package com.example.authservice.entity;

import com.example.authservice.utils.Roles;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.CollectionId;

import java.util.UUID;


@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;


    @NotNull
    private String firstname;

    @NotNull
    private String lastname;

    @NotNull
    private String email;

    @NotNull
    private String password;

    @Embedded
    private Date details;


}
