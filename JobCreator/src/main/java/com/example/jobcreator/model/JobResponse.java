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
@AllArgsConstructor
@NoArgsConstructor
public class JobResponse {

    private UUID jobId;

    @NotNull
    private int level;

    @NotNull
    private String jobTitle;

    @NotNull
    @Lob
    private String jobDesc;

    private List<String> coreSkills;

}
