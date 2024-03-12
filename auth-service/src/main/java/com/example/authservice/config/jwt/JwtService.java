package com.example.authservice.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtService extends AbstractJwt{


    //128bit key
    public static final String SECRET = "357538782F413F4428472B4B6250655368566D59703373367639792442264529";


    long thirtyDaysInMillis = 30L * 24L * 60L * 60L * 1000L;

    public String generateToken(String user_id, String user_name){
        System.out.println(user_id);
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims,user_id,user_name);
    };

    private String createToken(Map<String,Object> claims, String user_id,String user_name){
        return Jwts.builder()
                .setClaims(claims)
                .claim("user_id",user_id)
                .claim("user_name", user_name)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + thirtyDaysInMillis))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean validateToken(String token){

        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(getSignKey())
                    .build()
                    .parseClaimsJws(token);

            Claims claims = claimsJws.getBody();

            Date currentDate = new Date();
            Date issuedDate = claims.getIssuedAt();
            Date expirationDate = claims.getExpiration();

            System.out.println("Current Date: " + currentDate);
            System.out.println("Issued Date: " + issuedDate);
            System.out.println("Expiration Date: " + expirationDate);

            return !currentDate.before(issuedDate) && !currentDate.after(expirationDate);
        } catch (Exception e) {
            // Handle token parsing or expiration exception
            return false;
        }

    }

}