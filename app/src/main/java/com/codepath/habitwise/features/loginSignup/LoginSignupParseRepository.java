package com.codepath.habitwise.features.loginSignup;

import android.util.Log;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.io.File;

public class LoginSignupParseRepository implements ILoginSignupRepository{

    public final String TAG = "LoginSignupParseRepository";
    private static LoginSignupParseRepository loginSignupParseRepository;
    private final String KEY_FIRSTNAME = "firstName";
    private final String KEY_LASTNAME = "lastName";
    private final String KEY_DISPLAY_PIC = "displayPic";

    private LoginSignupParseRepository() {}


    public static ILoginSignupRepository getInstance() {
        LoginSignupParseRepository repo = loginSignupParseRepository;
        if (repo == null) {
            loginSignupParseRepository = new LoginSignupParseRepository();
        }
        return loginSignupParseRepository;
    }

    @Override
    public void loginUser(String username, String password, final ILoginEventListner eventListner) {
        Log.i(TAG, "Login request initialised on parse");
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                Log.i(TAG, "Login Successful");
                if (e != null) eventListner.onFailedLogin();
                eventListner.onSuccessfulLogin();
            }
        });
    }

    @Override
    public void signupUser(String email, String password, String firstName, String lastName, String username, File image, final ISignupEventListner eventListner) {
        Log.i(TAG, "Signup request initialised on parse");

        ParseUser user = new ParseUser();
        user.setEmail(email);
        user.setPassword(password);
        user.setUsername(username);
        user.put(KEY_FIRSTNAME, firstName);
        user.put(KEY_LASTNAME, lastName);
        if (image != null) {
            user.put(KEY_DISPLAY_PIC, new ParseFile(image));
        }

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    eventListner.onSuccessfulSignup();
                } else {
                    eventListner.onFailedSignup(e);
                }
            }
        });
    }
}
