package com.codepath.habitwise;

import android.app.Application;

import com.codepath.habitwise.models.Analysis;
import com.codepath.habitwise.models.Friends;
import com.codepath.habitwise.models.Habit;
import com.codepath.habitwise.models.HabitUserMapping;
import com.codepath.habitwise.models.Task;
import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {
    // Initializes Parse SDK as soon as the application is created
    @Override
    public void onCreate() {
        super.onCreate();
        ParseObject.registerSubclass(Habit.class);

        ParseObject.registerSubclass(Friends.class);
        ParseObject.registerSubclass(Habit.class);
        ParseObject.registerSubclass(Task.class);
        ParseObject.registerSubclass(HabitUserMapping.class);
        ParseObject.registerSubclass(Analysis.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("JU8NDB8cV6IjDS3tkTf1F1ArrvVu5g73MTgxf2Eo")
                .clientKey("0qhWBdLwcaKah8jwFbjCplw9OExFXT9RKwirT0w3")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
