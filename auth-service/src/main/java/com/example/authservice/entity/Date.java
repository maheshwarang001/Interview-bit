package com.example.authservice.entity;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Date {

    @NotNull
    private LocalDateTime createdAt;
    private LocalDateTime updatedAT;

    private LocalDateTime expires;

}
