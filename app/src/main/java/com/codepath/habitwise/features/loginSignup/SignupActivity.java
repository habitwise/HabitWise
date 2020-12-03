package com.codepath.habitwise.features.loginSignup;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codepath.habitwise.R;
import com.codepath.habitwise.features.Utilities;
import com.google.android.material.snackbar.Snackbar;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class SignupActivity extends AppCompatActivity implements ISignupEventListner{

    private final String TAG = "SIGNUP_ACTIVITY";
    private Context context = this;

    EditText etFirstName;
    EditText etLastName;
    EditText etEmail;
    EditText etPassword;
    EditText etConfirmPassword;
    Button btnSignup;
    Button btnAddDisplayPic;
    View viewSignup;
    CircleImageView displayPic;
    Bitmap displayPicBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        init();
    }

    private void init() {
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        viewSignup = findViewById(R.id.viewSignup);
        btnSignup = findViewById(R.id.btnSignUp);
        btnAddDisplayPic = findViewById(R.id.btnAddDisplayPic);
        displayPic = findViewById(R.id.profile_image);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        btnAddDisplayPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage(context);
            }
        });
    }

    private void signup() {
        String firstName = etFirstName.getText().toString();
        String lastName = etLastName.getText().toString();
        String password = etPassword.getText().toString();
        String email = etEmail.getText().toString();
        File image = null;

        if (firstName == null || firstName.trim() == "") {
            Snackbar.make(viewSignup, "First name is required", Snackbar.LENGTH_LONG).show();
            return;
        }

        if (lastName == null || lastName.trim() == "") {
            Snackbar.make(viewSignup, "Last name is required", Snackbar.LENGTH_LONG).show();
            return;
        }

        if (!isValidEmail(etEmail.getText().toString())) {
            Snackbar.make(viewSignup, "Enter valid email address", Snackbar.LENGTH_LONG).show();
            return;
        }

        if (!etPassword.getText().toString().equals(etConfirmPassword.getText().toString())) {
            Snackbar.make(viewSignup, "Password and Confirm password don't match", Snackbar.LENGTH_LONG).show();
            return;
        }

        // TODO: This validation should be using dialog instead of Toast and should showcase conditions for valid password
        if (!isValidPassword(etPassword.getText().toString())) {
            Snackbar.make(viewSignup, "Enter valid password", Snackbar.LENGTH_LONG).show();
            return;
        }


        try {
            if (displayPicBitmap != null) {
                image = Utilities.bitmapToFile(displayPicBitmap, this);
            }
        } catch (IOException ioe) {
            Log.e(TAG, "Exception occured while converting bitmap to file: " + ioe.getMessage());
            image = null;
        }

        ILoginSignupRepository loginSignupParseRepository = LoginSignupParseRepository.getInstance();
        loginSignupParseRepository.signupUser(email, password, firstName, lastName, email, image, this);
    }

    private boolean isValidPassword(String password) {
        if (password == null) {
            return false;
        }
        String regex = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=])"
                + "(?=\\S+$).{8,20}$";

        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(password);
        return m.matches();
    }

    private boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher m = pattern.matcher(email);
        return m.matches();
    }

    private void clearFocus() {
        etEmail.clearFocus();
        etFirstName.clearFocus();
        etLastName.clearFocus();
        etPassword.clearFocus();
        etConfirmPassword.clearFocus();
    }

    private void clearForm() {
        etEmail.setText("");
        etFirstName.setText("");
        etLastName.setText("");
        etPassword.setText("");
        etConfirmPassword.setText("");
    }

    @Override
    public void onSuccessfulSignup() {
        Log.i(TAG, "User signup successful");
        Utilities.hideSoftKeyboard(this);
        clearFocus();
        clearForm();
        Snackbar.make(viewSignup, "User signup successful", Snackbar.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void onFailedSignup(Exception e) {
        Log.e(TAG, "User signup failed: " + e.getMessage());
        Utilities.hideSoftKeyboard(this);
        clearFocus();
        Snackbar.make(viewSignup, e.getMessage(), Snackbar.LENGTH_LONG).show();
    }

    private void selectImage(Context context) {

        Utilities.verifyStoragePermissions(this);

        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose your profile picture");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);

                } else if (options[item].equals("Choose from Gallery")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto , 1);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        displayPicBitmap = (Bitmap) data.getExtras().get("data");
                        displayPic.setImageBitmap(displayPicBitmap);
                    }

                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri selectedImage =  data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        if (selectedImage != null) {
                            Cursor cursor = getContentResolver().query(selectedImage,
                                    filePathColumn, null, null, null);
                            if (cursor != null) {
                                cursor.moveToFirst();

                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                String picturePath = cursor.getString(columnIndex);
                                displayPicBitmap = BitmapFactory.decodeFile(picturePath);
                                displayPic.setImageBitmap(displayPicBitmap);
                                cursor.close();
                            }
                        }

                    }
                    break;
            }
        }
    }

}