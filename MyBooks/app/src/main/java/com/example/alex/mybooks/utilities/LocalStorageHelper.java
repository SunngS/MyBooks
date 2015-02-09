package com.example.alex.mybooks.utilities;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.alex.mybooks.entities.User;

import java.io.File;


/**
 * Created by v0cn115 on 2015/1/21.
 */
public class LocalStorageHelper {

    private final String LOCAL_LOGIN_RECORD_FILE = "LOGIN_RECORD";
    private Context callContext;

    public LocalStorageHelper(Context context){
        callContext = context;
    }

    public boolean HasLocalLoginRecord(){
        boolean hasLocalRecord =false;
        SharedPreferences loginRecord = callContext.getSharedPreferences(LOCAL_LOGIN_RECORD_FILE,Context.MODE_PRIVATE);
        if (loginRecord != null){
           hasLocalRecord = loginRecord.contains("USERNAME");
        }
        return hasLocalRecord;
    }

    public void SaveUserLoginRecord(User validUser) {
        SharedPreferences loginRecord = callContext.getSharedPreferences(LOCAL_LOGIN_RECORD_FILE,Context.MODE_PRIVATE);
        SharedPreferences.Editor recordEditor = loginRecord.edit();
        recordEditor.putString("USERNAME",validUser.getUsername());
        recordEditor.putString("STATE",validUser.getState());
        recordEditor.commit();
    }
}
