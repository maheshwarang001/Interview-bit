package com.example.roomregisterservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.Index;
import org.springframework.cglib.core.Local;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Data
@Document(collection = "rooms")
public class Room {



    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID roomID;

    @ElementCollection
    private Map<Role,UserDetails> userList;

    private byte[] pdfIntBytes;

    private UUID jobId;


    @Indexed(name ="schedule", expireAfter = "60m")
    private LocalDateTime schedule;


    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
