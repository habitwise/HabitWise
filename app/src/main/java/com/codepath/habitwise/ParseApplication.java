package com.codepath.habitwise;

import android.app.Application;

import com.parse.Parse;

public class ParseApplication extends Application {
    // Initializes Parse SDK as soon as the application is created
    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("JU8NDB8cV6IjDS3tkTf1F1ArrvVu5g73MTgxf2Eo")
                .clientKey("0qhWBdLwcaKah8jwFbjCplw9OExFXT9RKwirT0w3")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
