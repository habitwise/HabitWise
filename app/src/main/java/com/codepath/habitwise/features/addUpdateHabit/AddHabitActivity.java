package com.codepath.habitwise.features.addUpdateHabit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import com.codepath.habitwise.R;
import com.codepath.habitwise.models.Habit;
import com.google.android.material.card.MaterialCardView;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AddHabitActivity extends AppCompatActivity {
    public static final String TAG = "AddHabitActivity";
    private EditText etTitle;
    private EditText etFrequency;
    private Button btnSubmit;
    private List<Integer> days;
    private Integer recurrence;
    Habit habit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habit);
        habit = getIntent().getParcelableExtra("habit");;

        etTitle = findViewById(R.id.etTitle);
        etFrequency = findViewById(R.id.etFrequency);
        btnSubmit = findViewById(R.id.btnSubmit);
        days = new ArrayList<>();

        etTitle.setText(habit.getTitle());
        etFrequency.setText(habit.getFrequency());

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
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_daily:
                if (checked)
                    recurrence = 0;
                    Log.i(TAG, recurrence.toString());
                    break;
            case R.id.radio_weekly:
                if (checked)
                    recurrence = 1;
                    Log.i(TAG, recurrence.toString());
                    break;
        }
    }

    private void saveHabit(String title, int frequency) {
        Collections.sort(days);

        habit.setTitle(title);
        habit.setFrequency(frequency);
        habit.setRecurrence(recurrence);
        habit.setDays((ArrayList) days);
        habit.setStatus("Incomplete");
        habit.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e!=null) {
                    Toast.makeText(AddHabitActivity.this, "Error while saving",Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(AddHabitActivity.this, "New habit saved!",Toast.LENGTH_LONG).show();
                    Log.i(TAG, "Habit save successful");
                }
            }
        });
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkbox_mon:
                if (checked) {
                    days.add(1);
                }
                else {
                    days.remove(new Integer(1));
                }
                break;
            case R.id.checkbox_tue:
                if (checked) {
                    days.add(2);
                }
                else {
                    days.remove(new Integer(2));
                }
                break;
            case R.id.checkbox_wed:
                if (checked) {
                    days.add(3);
                }
                else {
                    days.remove(new Integer(3));
                }
                break;
            case R.id.checkbox_thu:
                if (checked) {
                    days.add(4);
                }
                else {
                    days.remove(new Integer(4));
                }
                break;
            case R.id.checkbox_fri:
                if (checked) {
                    days.add(5);
                }
                else {
                    days.remove(new Integer(5));
                }
                break;
            case R.id.checkbox_sat:
                if (checked) {
                    days.add(6);
                }
                else {
                    days.remove(new Integer(6));
                }
                break;
            case R.id.checkbox_sun:
                if (checked) {
                    days.add(0);
                }
                else {
                    days.remove(new Integer(0));
                }
                break;
        }
    }
}