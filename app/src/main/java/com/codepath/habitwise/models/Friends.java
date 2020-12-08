package com.codepath.habitwise.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Friends")
public class Friends extends ParseObject {
    public static final String KEY_FROM_USER = "fromUser";
    public static final String KEY_TO_USER = "toUser";
    public static final String KEY_STATUS = "Status";

    public ParseUser getFromUser(){
        return getParseUser(KEY_FROM_USER);
    }

    public ParseUser getToUser(){
        return getParseUser(KEY_TO_USER);
    }

    public void setFromUser(ParseUser user){
        put(KEY_FROM_USER, user);
    }

    public void setToUser(ParseUser user){
        put(KEY_TO_USER, user);
    }

    public String getStatus(){
        return getString(KEY_STATUS);
    }

    public void setStatus(String status){
        put(KEY_STATUS, status);
    }
}
