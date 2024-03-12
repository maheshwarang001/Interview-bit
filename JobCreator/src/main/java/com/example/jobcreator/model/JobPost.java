package com.example.jobcreator.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class JobPost {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID jobId;

    private UUID recruiter;

    @NotNull
    private int level;

    @NotNull
    private String jobTitle;

    @NotNull
    @Embedded
    private EncoderData jobDesc;

    @ElementCollection
    private List<String> coreSkills;


    private LocalDateTime createdAt;
}
