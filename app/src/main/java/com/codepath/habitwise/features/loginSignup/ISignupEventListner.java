package com.codepath.habitwise.features.loginSignup;

import com.parse.ParseUser;

public interface ISignupEventListner {
    void onSuccessfulSignup();
    void onFailedSignup(Exception e);
}
