package com.example.jobcreator.repository;

import com.example.jobcreator.model.JobPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JobRepo extends JpaRepository<JobPost,UUID> {

    List<JobPost> findAllByRecruiter(UUID id);


    boolean existsByJobIdAndRecruiter(UUID jobID, UUID rID);

    void deleteByJobId(UUID id);


}
