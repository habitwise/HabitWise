package com.codepath.habitwise.features.loginSignup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.codepath.habitwise.R;
import com.codepath.habitwise.features.MainActivity;
import com.google.android.material.snackbar.Snackbar;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity implements ILoginEventListner {

    public static final String TAG = "LoginActivity";
    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnSignup;
    private Context context;
    private View viewLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (ParseUser.getCurrentUser() != null) {
            goMainActivity();
        }

        init();
    }

    // Initializing Login activity
    public void init() {
        // Initialize UI components
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignup = findViewById(R.id.btnSignup);
        viewLogin = findViewById(R.id.viewLogin);

        context = this;

        // Setup Listners
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Login process initialized for user: " + etUsername.getText().toString());
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                login(username, password);
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Redirecting to signup activity");
                Intent signupIntent = new Intent(context, SignupActivity.class);
                startActivity(signupIntent);
            }
        });
    }

    public void login(String username, String password) {
        ILoginSignupRepository loginSignupParseRepository = LoginSignupParseRepository.getInstance();
        loginSignupParseRepository.loginUser(username, password, this);
    }

    @Override
    public ParseUser onSuccessfulLogin() {
        Log.i(TAG, "onSuccessfulLogin");
        Snackbar.make(viewLogin, "Logged in", Snackbar.LENGTH_LONG).show();
        Intent loginIntent = new Intent(context, MainActivity.class);
        startActivity(loginIntent);
        return null;
    }

    @Override
    public Exception onFailedLogin() {
        Log.i(TAG, "onFailedLogin");
        Snackbar.make(viewLogin, "Invalid account credentials", Snackbar.LENGTH_LONG).show();
        return null;
    }

    private void goMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
