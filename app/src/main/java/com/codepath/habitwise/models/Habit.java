package com.codepath.habitwise.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
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
    public String getFrequency() {return getString(key_frequency);}

    //setters
    public void setTitle(String title) {
        put(key_title, title);
    }
    public void setUser(String user) { put(key_user, user); }
    public void setRecurrence(int recurrence) {
        put(key_recurrence, recurrence);
    }
    public void setFrequency(int frequency) {
        put(key_frequency, frequency);
    }
    public void setDays(ArrayList arrayList) { put(key_days, arrayList);}
    public void setStatus(String status) { put(key_status, status);}
}
