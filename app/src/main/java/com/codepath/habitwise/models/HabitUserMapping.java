package com.codepath.habitwise.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("HabitUserMapping")
public class HabitUserMapping extends ParseObject {
    public static final String key_habit = "habit";
    public static final String key_user = "user";

    public ParseUser getUser() {return getParseUser(key_user);}
    public Habit getHabit() {return (Habit) getParseObject(key_habit);}

    public void setUser(ParseUser user){put(key_user, user);}
    public void setHabit(Habit habit){put(key_habit, habit);}
}
