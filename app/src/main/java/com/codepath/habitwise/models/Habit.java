package com.codepath.habitwise.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.json.JSONArray;

import java.util.ArrayList;

@ParseClassName("Habit")
public class Habit extends ParseObject {
    public static final String key_title = "title";
    public static final String key_user = "user";
    public static final String key_recurrence = "recurrence";
    public static final String key_frequency = "frequency";
    public static final String key_days = "days";
    public static final String key_status = "status";

    //getters
    public String getTitle() {return getString(key_title);}
    public ParseUser getUser() {
        return getParseUser(key_user);
    }
    public Integer getRecurrence()  { return getInt(key_recurrence); }
    public Integer getFrequency() {return getInt(key_frequency);}
    //public JSONArray getDays() {return getJSONArray(key_days);}
    public String getStatus() {return getString(key_status);}


    //setters
    public void setTitle(String title) { put(key_title, title); }
    public void setUser(ParseUser user) { put(key_user, user); }
    public void setRecurrence(int recurrence) {
        put(key_recurrence, recurrence);
    }
    public void setFrequency(int frequency) {
        put(key_frequency, frequency);
    }
    public void setDays(ArrayList arrayList) { put(key_days, arrayList);}
    public void setStatus(String status) { put(key_status, status);}
}
