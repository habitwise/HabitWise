package com.codepath.habitwise.features.loginSignup;

import com.parse.ParseUser;

public interface ILoginEventListner {
    ParseUser onSuccessfulLogin();
    Exception onFailedLogin();
}
