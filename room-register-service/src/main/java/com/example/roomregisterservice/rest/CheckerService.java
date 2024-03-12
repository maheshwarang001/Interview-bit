package com.example.roomregisterservice.rest;


import com.example.roomregisterservice.config.RestConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class CheckerService {


    @Autowired
    RestConfig restConfig;


    public boolean checkRecruiterAndJobId(UUID jId, UUID rId){


        String url = "http://localhost:8083/job-post/v1/auth/room-id?jobID=" + jId + "&recruiterID=" + rId;

        Boolean response = restConfig.restTemplate().getForObject(url, Boolean.class);

        if (response != null) {
            return response;
        } else {

            throw new RuntimeException("Response from other microservice is null");

        }
    }


}
