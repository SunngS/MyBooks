package com.example.alex.mybooks.entities;

import java.util.Date;

/**
 * Created by v0cn115 on 2015/1/23.
 */
public class User {
    private String username;
    private String password;
    private String state;

    public String getUsername(){
        return username;
    }

    public void setUsername(String value){
        username = value;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String value){
        password=value;
    }

    public String getState(){
        return state;
    }

    public void setState(String value){
        state = value;
    }
}
