package com.codepath.habitwise.models;

import com.parse.Parse;
import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

@ParseClassName("Habit")
public class Habit extends ParseObject {
    public static final String key_title = "title";
    public static final String key_recurrence = "recurrence";
    public static final String key_frequency = "frequency";
    public static final String key_days = "days";
    public static final String key_status = "status";
    public static final String key_shared = "shared";


    //getters
    public String getTitle() {return getString(key_title);}
    public String getObj() {return getObjectId();}
    public int getRecurrence() {return getInt(key_recurrence);}
    public int getFrequency() {return getInt(key_frequency);}
    public List<Integer> getDays() {return getList(key_days);}
    public String getStatus() {return getString(key_status);}
    public Boolean getShared() {return getBoolean(key_shared);}

    //setters
    public void setTitle(String title) {
        put(key_title, title);
    }
    public void setRecurrence(int recurrence) {
        put(key_recurrence, recurrence);
    }
    public void setFrequency(int frequency) {
        put(key_frequency, frequency);
    }
    public void setDays(List<Integer> listOfDays) { put(key_days, listOfDays);}
    public void setStatus(String status) { put(key_status, status);}
    public void setShared(Boolean shared) { put(key_shared, shared);}
}
