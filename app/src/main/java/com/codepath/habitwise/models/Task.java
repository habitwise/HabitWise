package com.codepath.habitwise.models;
import com.parse.ParseClassName;
import com.parse.ParseObject;
import java.util.ArrayList;

@ParseClassName("Task")

public class Task extends ParseObject {
    public static final String key_counter = "counter";


    //getters
    public int getCounter() {return getInt(key_counter);}

    //setters


}
