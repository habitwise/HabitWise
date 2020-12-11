package com.codepath.habitwise.features.addUpdateHabit;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.codepath.habitwise.R;
import com.codepath.habitwise.features.userProfile.FriendsListAdapter;
import com.codepath.habitwise.features.userProfile.IUserProfileEventListner;
import com.codepath.habitwise.features.userProfile.IUserProfileRepository;
import com.codepath.habitwise.features.userProfile.UserProfileParseRepository;
import com.codepath.habitwise.models.Friends;
import com.codepath.habitwise.models.Habit;
import com.codepath.habitwise.models.HabitUserMapping;
import com.google.android.material.card.MaterialCardView;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AddHabitActivity extends AppCompatActivity implements IUserProfileEventListner {
    public static final String TAG = "AddHabitActivity";
    public static final String INIT_STATUS = "Incomplete";
    public static final List<String> DAYS_OF_WEEK = new ArrayList<String>(
            Arrays.asList("Sun","Mon","Tue","Wed,","Thu","Fri","Sat"));
    private EditText etTitle;
    private EditText etFrequency;
    private Button btnSubmit;
    private List<Integer> days;
    private Integer recurrence;
    private Spinner spinnerFriends;
    private ParseUser selectedFriend;
    private List<ParseUser> friends;
    private Boolean sharedHabit;
    Habit habit;
    HabitUserMapping habitUserMapping;
    HabitUserMapping habitUserFriendMapping;
    private FriendsSpinnerAdapter friendsSpinnerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habit);
        habit = getIntent().getParcelableExtra("habit");;
        habitUserMapping = new HabitUserMapping();
        habitUserFriendMapping = new HabitUserMapping();

        etTitle = findViewById(R.id.etTitle);
        etFrequency = findViewById(R.id.etFrequency);
        btnSubmit = findViewById(R.id.btnSubmit);
        sharedHabit = false;

        //initialize all the checkboxes to be checked
        days = new ArrayList<Integer>(Arrays.asList(1,2,3,4,5,6,7));
        LinearLayout checkboxesView = (LinearLayout) findViewById(R.id.checkboxes);
        for ( int i=0; i<checkboxesView.getChildCount(); i++) {
            CheckBox currentCheckBox = (CheckBox) checkboxesView.getChildAt(i);
            currentCheckBox.setChecked(true);
        }

      //  etTitle.setText(habit.getTitle());
      //  etFrequency.setText(habit.getFrequency());
        friends = new ArrayList<>();
        friends.add(ParseUser.getCurrentUser());

        IUserProfileRepository repository = UserProfileParseRepository.getInstance();
        repository.fetchFriendsList(this);

        spinnerFriends = findViewById(R.id.spinner_friends);
        friendsSpinnerAdapter = new FriendsSpinnerAdapter(this, (ArrayList<ParseUser>) friends);
        spinnerFriends.setAdapter(friendsSpinnerAdapter);
        spinnerFriends.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent,
                                               View view, int position, long id)
                    {
                        // It returns the clicked item.
                        ParseUser clickedItem = (ParseUser)
                                parent.getItemAtPosition(position);
                        String name = clickedItem.getUsername();
                        selectedFriend = clickedItem;
                        Log.i(TAG, "selected " + name);
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent)
                    {

                    }
                });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etTitle.getText().toString();
                String frequency_text = etFrequency.getText().toString();
                if (title.equals("")) {
                    Toast.makeText(AddHabitActivity.this, "Title cannot be empty",Toast.LENGTH_LONG).show();
                    return;
                }
                if (frequency_text.equals("")) {
                    Toast.makeText(AddHabitActivity.this, "Frequency cannot be empty",Toast.LENGTH_LONG).show();
                    return;
                }
                int frequency = Integer.parseInt(frequency_text);
                ParseUser currentUser = ParseUser.getCurrentUser();
                saveHabit(title, currentUser, frequency);
                finish();
            }
        });
    }
//    public void onRadioButtonClicked(View view) {
//        // Is the button now checked?
//        boolean checked = ((RadioButton) view).isChecked();
//        // Check which radio button was clicked
//        switch(view.getId()) {
//            case R.id.radio_daily:
//                if (checked)
//                    recurrence = 0;
//                    Log.i(TAG, recurrence.toString());
//                    days = new ArrayList<Integer>(
//                        Arrays.asList(1,2,3,4,5,6,7));
//                    LinearLayout checkboxesView = (LinearLayout) findViewById(R.id.checkboxes);
//                    for ( int i=0; i<checkboxesView.getChildCount(); i++) {
//                        CheckBox currentCheckBox = (CheckBox) checkboxesView.getChildAt(i);
//                        currentCheckBox.setChecked(true);
//                    }
//                break;
//            case R.id.radio_weekly:
//                if (checked)
//                    recurrence = 1;
//                    Log.i(TAG, recurrence.toString());
//                    break;
//        }
//    }

    private void saveHabit(String title, final ParseUser currentUser, int frequency) {
        Collections.sort(days);

        //if user would like to share habit with friend
        if (selectedFriend != currentUser) {
            sharedHabit = true;
        }

        if (days.size() == 7) {
            recurrence = 0; //daily task
        } else {
            recurrence = 1; //weekly task
        }

        Log.i(TAG, "selected dropdown: " +selectedFriend);
        habit.setTitle(title);
        habit.setFrequency(frequency);
        habit.setRecurrence(recurrence);
        habit.setDays((ArrayList) days);
        habit.setStatus(INIT_STATUS);
        habit.setShared(sharedHabit);
        habit.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e!=null) {
                    Toast.makeText(AddHabitActivity.this, "Error while saving",Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(AddHabitActivity.this, "New habit saved!",Toast.LENGTH_LONG).show();
                    Log.i(TAG, "New habit created");

                    habitUserMapping.setHabit(habit);
                    habitUserMapping.setUser(currentUser);
                    habitUserMapping.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e!=null) {
                                Log.i(TAG, "Error while saving HabitUserMapping");
                            }
                            else {
                                //Toast.makeText(AddHabitActivity.this, "New HabitUserMapping saved!",Toast.LENGTH_LONG).show();
                                Log.i(TAG, "HabitUserMapping save successful");
                            }
                        }
                    });

                    //duplicating the habit object for the friend
                    if (sharedHabit) {
                        habitUserFriendMapping.setHabit(habit);
                        habitUserFriendMapping.setUser(selectedFriend);
                        habitUserFriendMapping.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e!=null) {
                                    Log.i(TAG, "Error while saving HabitUserMapping for friend");
                                }
                                else {
                                    //Toast.makeText(AddHabitActivity.this, "New HabitUserMapping saved!",Toast.LENGTH_LONG).show();
                                    Log.i(TAG, "HabitUserMapping for friend save successful");
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    // add items into spinner dynamically
    public void addItemsOnFriendsSpinner(List<ParseUser> friends) {
        Log.i(TAG, "friends " + friends.toString());
        List<String> list = new ArrayList<String>();
        list.add("list 1");
        list.add("list 2");
        list.add("list 3");
        ArrayAdapter<ParseUser> dataAdapter = new ArrayAdapter<ParseUser>(this,
                android.R.layout.simple_spinner_item, friends);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFriends.setAdapter(dataAdapter);
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();
        Log.i(TAG, days.toString());
        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkbox_sun:
                if (checked) {
                    days.add(1);
                }
                else {
                    days.remove(new Integer(1));
                }
                break;
            case R.id.checkbox_mon:
                if (checked) {
                    days.add(2);
                }
                else {
                    days.remove(new Integer(2));
                }
                break;
            case R.id.checkbox_tue:
                if (checked) {
                    days.add(3);
                }
                else {
                    days.remove(new Integer(3));
                }
                break;
            case R.id.checkbox_wed:
                if (checked) {
                    days.add(4);
                }
                else {
                    days.remove(new Integer(4));
                }
                break;
            case R.id.checkbox_thu:
                if (checked) {
                    days.add(5);
                }
                else {
                    days.remove(new Integer(5));
                }
                break;
            case R.id.checkbox_fri:
                if (checked) {
                    days.add(6);
                }
                else {
                    days.remove(new Integer(6));
                }
                break;
            case R.id.checkbox_sat:
                if (checked) {
                    days.add(7);
                }
                else {
                    days.remove(new Integer(7));
                }
                break;
        }
    }

    @Override
    public void fetchFriendsListSuccessful(List<ParseUser> newFriendsList) {
        friends.clear();
        friends.add(ParseUser.getCurrentUser());
        friends.addAll(newFriendsList);
        updateRvFriendsList();
        //addItemsOnFriendsSpinner(newFriendsList);

        Log.i(TAG, "new friends" + newFriendsList.toString());
    }

    @Override
    public void fetchFriendsListFailed(Exception e) {
        if (e != null) {
            //Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG);
            Log.e(TAG, "cannot fetch friends " + e.getMessage());
        }
    }

    @Override
    public void updateRvFriendsList() {
        //friendsListAdapter.notifyDataSetChanged();
        friendsSpinnerAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadLatestFriendsList() {
        IUserProfileRepository repository = UserProfileParseRepository.getInstance();
        repository.fetchFriendsList(this);
    }

    @Override
    public void fetchFriendRequestsSuccessful(List<Friends> newFriendRequests) {
//        friendRequests.clear();
//        friendRequests.addAll(newFriendRequests);
//        updateRvFriendRequests();
    }

    @Override
    public void fetchFriendRequestsFailed(Exception e) {
//        if (e != null) {
//            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG);
//        }
    }

    @Override
    public void updateRvFriendRequests() {

  //      friendRequestAdapter.notifyDataSetChanged();
    }

}