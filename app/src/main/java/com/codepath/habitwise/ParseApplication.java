package com.codepath.habitwise;

import android.app.Application;

import com.codepath.habitwise.models.Habit;
import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {
    // Initializes Parse SDK as soon as the application is created
    @Override
    public void onCreate() {
        super.onCreate();
        ParseObject.registerSubclass(Habit.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("JU8NDB8cV6IjDS3tkTf1F1ArrvVu5g73MTgxf2Eo")
                .clientKey("0qhWBdLwcaKah8jwFbjCplw9OExFXT9RKwirT0w3")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
