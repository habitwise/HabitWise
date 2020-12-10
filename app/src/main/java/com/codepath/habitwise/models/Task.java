package com.codepath.habitwise.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.Date;

@ParseClassName("Task")
public class Task extends ParseObject {
    public static final String key_task_date = "taskDate";
    public static final String key_counter = "counter";
    public static final String key_user = "user";
    public static final String key_habit = "habit";

    public Date getTaskDate() {return getDate(key_task_date);}
    public int  getCounter() {return getInt(key_counter);}
    public ParseUser getUser() {return getParseUser(key_user);}
    public Habit getHabit() {return (Habit) getParseObject(key_habit);}

    public void setTaskDate(Date taskDate){put(key_task_date, taskDate);}
    public void setCounter(int counter){put(key_counter, counter);}
    public void setUser(ParseUser user){put(key_user, user);}
    public void setHabit(Habit habit){put(key_habit, habit);}
}
