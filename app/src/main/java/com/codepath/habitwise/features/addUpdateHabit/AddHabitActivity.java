package com.codepath.habitwise.features.addUpdateHabit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.codepath.habitwise.R;
import com.codepath.habitwise.models.Habit;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class AddHabitActivity extends AppCompatActivity {
    public static final String TAG = "AddHabitActivity";
    private EditText etTitle;
    private EditText etFrequency;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habit);

        etTitle = findViewById(R.id.etTitle);
        etFrequency = findViewById(R.id.etFrequency);
        btnSubmit = findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etTitle.getText().toString();
                int frequency = Integer.parseInt(etFrequency.getText().toString());
                if (title.isEmpty()) {
                    Toast.makeText(AddHabitActivity.this, "Title cannot be empty",Toast.LENGTH_LONG).show();
                    return;
                }
                Log.i(TAG, "Button clicked");
                saveHabit(title, frequency);
                finish();
            }
        });
    }

    private void saveHabit(String title, int frequency) {
        Habit habit = new Habit();
        habit.setTitle(title);
        habit.setFrequency(frequency);
        habit.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e!=null) {
                    Toast.makeText(AddHabitActivity.this, "Error while saving",Toast.LENGTH_LONG).show();
                }
                Log.i(TAG, "Habit save successful");
                etTitle.setText("");
                etFrequency.setText("");
            }
        });
    }

}