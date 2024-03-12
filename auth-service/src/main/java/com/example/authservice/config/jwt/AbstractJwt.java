package com.example.authservice.config.jwt;

import java.security.Key;
import java.util.Map;

public abstract class AbstractJwt {
    public String generateToken(String user_id,String user_name){
        return null;
    };

    private String createToken(Map<String,Object> claims, String user_name){
        return null;
    }

    public boolean validateToken(String token){
        return false;
    }

    private Key getSignKey(){
        return null;
    }
}
