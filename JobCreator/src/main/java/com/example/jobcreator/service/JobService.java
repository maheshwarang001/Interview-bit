package com.example.jobcreator.service;

import com.example.jobcreator.dao.JobDao;
import com.example.jobcreator.model.EncoderData;
import com.example.jobcreator.model.JobPost;
import com.example.jobcreator.model.JobResponse;
import com.example.jobcreator.repository.JobRepo;
import com.example.jobcreator.utils.HuffmanCoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Service
public class JobService {

    ExecutorService thread = Executors.newFixedThreadPool(10);

    @Autowired
    JobDao jobDao;

    public boolean createJob(JobResponse job, UUID rid){

        try{

            thread.execute(()->{

                EncoderData encoderData = null;
                try {
                    encoderData = new HuffmanCoder(job.getJobDesc()).encode(job.getJobDesc());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                JobPost jobPost = new JobPost();

                jobPost.setRecruiter(rid);
                jobPost.setJobTitle(job.getJobTitle());
                jobPost.setJobDesc(encoderData);
                jobPost.setLevel(job.getLevel());
                jobPost.setCoreSkills(job.getCoreSkills());
                jobPost.setCreatedAt(LocalDateTime.now());
                jobDao.save(jobPost);

            });



        }catch (Exception ex){

            return false;
        }

        return true;
    }

    public List<JobResponse> getAll(UUID id){
        List<JobPost> list = jobDao.getAllByRecruiterId(id);

        HuffmanCoder huff = new HuffmanCoder();

        List<JobResponse> ans = new ArrayList<>();
        for(JobPost it: list){

            String desc = huff.decode(BitSet.valueOf(it.getJobDesc().getBytes()),it.getJobDesc().getDecoder());


            ans.add(new JobResponse(it.getJobId(),it.getLevel(),it.getJobTitle(),desc,it.getCoreSkills()));

        }

        return ans;
    }


    public boolean checkJobIDAndRecruiter(UUID jId, UUID rId){
        return jobDao.checkJidAndRId(jId,rId);
    }


}
