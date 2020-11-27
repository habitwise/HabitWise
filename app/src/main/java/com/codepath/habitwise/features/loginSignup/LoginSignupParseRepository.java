package com.codepath.habitwise.features.loginSignup;

import android.util.Log;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginSignupParseRepository implements ILoginSignupRepository{

    public final String TAG = "LoginSignupParseRepository";

    @Override
    public void loginUser(String username, String password) {
        Log.i(TAG, "Login request initialised on parse");
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {

            }
        });
    }

    @Override
    public void signupUser() {
        Log.i(TAG, "Signup request initialised on parse");
    }
}
