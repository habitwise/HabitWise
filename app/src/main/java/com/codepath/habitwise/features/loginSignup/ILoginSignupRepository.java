package com.codepath.habitwise.features.loginSignup;

import java.io.File;

public interface ILoginSignupRepository {

    void loginUser(String username, String password, final ILoginEventListner eventListner);
    void signupUser(String email, String password, String firstName, String lastName, String username, File image, final ISignupEventListner eventListner);

}
