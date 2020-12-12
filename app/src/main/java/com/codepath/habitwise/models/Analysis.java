package com.codepath.habitwise.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Analysis")
public class Analysis extends ParseObject {

    public static final String key_user1 = "user1";
    public static final String key_user2 = "user2";
    public static final String key_currentStreak = "currentStreak";
    public static final String key_highestStreak = "highestStreak";
    public static final String key_habit = "habit";
    public static final String key_isPersonal = "isPersonal";

    public ParseUser getUser1(){return getParseUser(key_user1);}
    public ParseUser getUser2(){return getParseUser(key_user2);}
    public int getCurrentStreak(){return getInt(key_currentStreak);}
    public int getHighestStreak(){return getInt(key_highestStreak);}
    public Habit getHabit(){return (Habit) getParseObject(key_habit);}
    public boolean isPersonal(){return getBoolean(key_isPersonal);}

}
