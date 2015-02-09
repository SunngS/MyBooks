package com.example.alex.mybooks.entities;

/**
 * Created by v0cn115 on 2015/1/29.
 */
public class LoginResult {

    public static final String LOGIN_SUCCEED = "LOGIN_SUCCEED";
    public static final String USER_NOT_EXISTS = "USER_NOT_EXISTS";
    public static final String PASSWORD_NOT_CORRECT = "PASSWORD_NOT_CORRECT";
    public static final String DEFAULT_RESULT_CODE = "SERVER_INTERNAL_ERROR";

    private boolean isSucceed;
    private String resultCode;
    private User loginUser;

    public String getResultCode(){
        return resultCode;
    }

    public void setResultCode(String value){
        resultCode = value;
    }

    public User getLoginUser(){
        return loginUser;
    }

    public void setLoginUser(User value){
        loginUser = value;
    }

    public boolean getSucceed(){
        return isSucceed;
    }

    public  void setSucceed(boolean value){
        isSucceed =value;
    }
}
