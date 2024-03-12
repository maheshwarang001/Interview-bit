package com.example.jobcreator.dao;


import com.example.jobcreator.model.JobPost;
import com.example.jobcreator.repository.JobRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class JobDao {

    @Autowired
    JobRepo repo;

    public void save(JobPost jobPost){
        repo.save(jobPost);
    }


    public List<JobPost> getAllByRecruiterId(UUID id){
        return repo.findAllByRecruiter(id);
    }

    public boolean checkJidAndRId(UUID jId, UUID rId){
        return repo.existsByJobIdAndRecruiter(jId,rId);
    }

    public void deleteById(UUID id){
         repo.deleteById(id);
    }


}
