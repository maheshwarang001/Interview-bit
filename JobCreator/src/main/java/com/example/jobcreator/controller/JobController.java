package com.example.jobcreator.controller;


import com.example.jobcreator.model.JobPost;
import com.example.jobcreator.model.JobResponse;
import com.example.jobcreator.service.JobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/job-post/v1")
public class JobController {


    @Autowired
    JobService service;


    @PostMapping("/create")
    public ResponseEntity<Map<String,String>> createJob(
            @RequestBody JobResponse jobPost,
            @RequestHeader("X-User-ID") String user_id
            ){

        try {

            if(user_id == null || user_id.isEmpty()) {
                System.out.println("ITS EMPTY");
                throw new AuthenticationException();
            }

            boolean ans = service.createJob(jobPost, UUID.fromString(user_id));


            if (ans) {
                return ResponseEntity.ok().body(Map.of("response", "success"));
            }
        }catch (Exception ex) {


            log.warn(ex.getMessage());

        }
        return ResponseEntity.badRequest().body(Map.of("response", "bad"));

    }



    @GetMapping("/getJobPostings")
    public ResponseEntity<Map<String, List<JobResponse>>> getAllByID(
            @RequestHeader("X-User-ID")String rUuid
    ){

        try {

            if(rUuid == null || rUuid.isEmpty()) {
                throw new AuthenticationException();
            }


            List<JobResponse> ans = service.getAll(UUID.fromString(rUuid));

            return ResponseEntity.ok().body(Map.of("response", ans));

        }catch (Exception ex) {

            return ResponseEntity.badRequest().body(Map.of("response", new ArrayList<>()));

        }


    }

    @GetMapping("/auth/room-id")
    public boolean checkJidAndRId(@RequestParam UUID jobID,
                                  @RequestParam UUID recruiterID ){
        return service.checkJobIDAndRecruiter(jobID,recruiterID);
    }






}
