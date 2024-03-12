package com.example.signal.websocket;

public class Helper {

    public static String getRoomIdFromUrl(String url){
        String [] u = url.split("/");
        return u[u.length-2];
    }

    public static String getUserIdFromUrl(String url){
        // Extract the substring after the last '/'
        String [] u = url.split("/");
        return u[u.length-1];
    }
}
