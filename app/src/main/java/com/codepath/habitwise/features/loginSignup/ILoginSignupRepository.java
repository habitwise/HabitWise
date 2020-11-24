package com.codepath.habitwise.features.loginSignup;

public interface ILoginSignupRepository {

    void loginUser(String username, String password);
    void signupUser();
}
